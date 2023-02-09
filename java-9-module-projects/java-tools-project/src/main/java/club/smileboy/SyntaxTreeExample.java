package club.smileboy;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;

import javax.lang.model.element.Element;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class SyntaxTreeExample {
    public static void main(String[] args) throws URISyntaxException, IOException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        URI url = ClassLoader.getSystemResource("files/Main.java").toURI();

        System.out.println(url);

        System.out.println("开始获取类文件 ..............");
        Iterable<? extends JavaFileObject> files = standardFileManager.getJavaFileObjectsFromFiles(List.of(new File(url)));

        JavaFileObject next = files.iterator().next();


        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, List.of(), null, List.of(next));

        Context context = ((JavacTaskImpl) task).getContext();
        JavacElements elements = JavacElements.instance(context);
        JavacTask javacTask = (JavacTask) task;
//        Iterable<? extends Element> analyze = javacTask.analyze();
//        Element rootElement = analyze.iterator().next();
//
//        FilteredMemberList allMembers = elements.getAllMembers(((TypeElement) rootElement));
//        System.out.println("all members .....");
//        for (Symbol allMember : allMembers) {
//            System.out.println(allMember.name);
//        }


        Trees instance = Trees.instance(task);
        Iterable<? extends CompilationUnitTree> unitTrees = ((JavacTask) task).parse();
        CompilationUnitTree Vtree = unitTrees.iterator().next();

        Iterable<? extends Element> analyze = javacTask.analyze();
//        Element next2 = analyze.iterator().next();
//        System.out.println(next2);

        Element element = analyze.iterator().next();
        JCTree jcTree = elements.getTree(element);

//
//        List<? extends Tree> typeDecls = tree.getTypeDecls();
//        Tree next1 = typeDecls.iterator().next();
//
//        JCTree next11 = (JCTree) next1;
        jcTree.accept(new TreeTranslator() {
            @Override
            public void visitSelect(JCTree.JCFieldAccess tree) {
                System.out.println("field " + tree.name);
            }

            @Override
            public void visitVarDef(JCTree.JCVariableDecl tree) {
                System.out.println("variable " + tree.name);

//                instance.getElement(TreePath.getPath(tree.getTree(),tree.getTree()));
            }
        });


        standardFileManager.close();

    }
}
