package org.mdkt.compiler;

import javafx.util.Pair;

import javax.tools.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Compile Java sources in-memory
 */
public class InMemoryJavaCompiler {
    boolean ignoreWarnings = false;
    private JavaCompiler javac;
    private DynamicClassLoader classLoader;
    private Iterable<String> options;
    private Map<String, SourceCode> sourceCodes = new HashMap<String, SourceCode>();

    private InMemoryJavaCompiler() {
        this.javac = ToolProvider.getSystemJavaCompiler();
        this.classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
    }

    public static InMemoryJavaCompiler newInstance() {
        return new InMemoryJavaCompiler();
    }

    public InMemoryJavaCompiler useParentClassLoader(ClassLoader parent) {
        this.classLoader = new DynamicClassLoader(parent);
        return this;
    }

    /**
     * @return the class loader used internally by the compiler
     */
    public ClassLoader getClassloader() {
        return classLoader;
    }

    /**
     * Options used by the compiler, e.g. '-Xlint:unchecked'.
     *
     * @param options
     * @return
     */
    public InMemoryJavaCompiler useOptions(String... options) {
        this.options = Arrays.asList(options);
        return this;
    }

    /**
     * Ignore non-critical compiler output, like unchecked/unsafe operation
     * warnings.
     *
     * @return
     */
    public InMemoryJavaCompiler ignoreWarnings() {
        ignoreWarnings = true;
        return this;
    }

    /**
     * Compile all sources
     *
     * @return Map containing instances of all compiled classes
     * @throws Exception
     */
    public Map<String, Class<?>> compileAll() {
        if (sourceCodes.size() == 0) {
            throw new CompilationException("No source code to compile");
        }
        Collection<SourceCode> compilationUnits = sourceCodes.values();
        CompiledCode[] code;

        code = new CompiledCode[compilationUnits.size()];
        Iterator<SourceCode> iter = compilationUnits.iterator();
        for (int i = 0; i < code.length; i++) {
            try {
                code[i] = new CompiledCode(iter.next().getClassName());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), classLoader);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, collector, options, null, compilationUnits);
        boolean result = task.call();
        if (!result || collector.getDiagnostics().size() > 0) {
            StringBuffer exceptionMsg = new StringBuffer();
            exceptionMsg.append("Unable to compile the source");
            boolean hasWarnings = false;
            boolean hasErrors = false;
            for (Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics()) {
                switch (d.getKind()) {
                    case NOTE:
                    case MANDATORY_WARNING:
                    case WARNING:
                        hasWarnings = true;
                        break;
                    case OTHER:
                    case ERROR:
                    default:
                        hasErrors = true;
                        break;
                }
                exceptionMsg.append("\n").append("[kind=").append(d.getKind());
                exceptionMsg.append(", ").append("line=").append(d.getLineNumber());
                exceptionMsg.append(", ").append("message=").append(d.getMessage(Locale.US)).append("]");
            }
            if (hasWarnings && !ignoreWarnings || hasErrors) {
                throw new CompilationException(exceptionMsg.toString());
            }
        }

        Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
        for (String className : sourceCodes.keySet()) {
            try {
                classes.put(className, classLoader.loadClass(className));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * Compile single source
     *
     * @param className
     * @param sourceCode
     * @return
     * @throws Exception
     */
    public Class<?> compile(String className, String sourceCode) {
        return addSource(className, sourceCode).compileAll().get(className);
    }

    /**
     * Add source code to the compiler
     *
     * @param className
     * @param sourceCode
     * @return
     * @throws Exception
     * @see {@link #compileAll()}
     */
    public InMemoryJavaCompiler addSource(String className, String sourceCode) {
        sourceCodes.put(className, new SourceCode(className, sourceCode));
        return this;
    }


    public Map<String, Pair<CompiledCode, Class<?>>> compileAllToRawBytes() {
        Map<String, Class<?>> stringClassMap = compileAll();
        Map<String, CompiledCode> customCompiledCode = this.classLoader.getCustomCompiledCode();
        return customCompiledCode.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new Pair<>(e.getValue(), stringClassMap.get(e.getKey()))));
    }

    public Pair<CompiledCode, Class<?>> compileToRawBytes(String className, String sourceCode) {
        return this.addSource(className, sourceCode).compileAllToRawBytes().get(className);
    }
}
