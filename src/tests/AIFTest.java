package tests;

import static org.junit.Assert.*;
import metrics.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import ast.ASTReader;
import ast.CompilationUnitCache;
import ast.SystemObject;

public class AIFTest {

	@Test
	public void test() {
		
		System.out.print("1) ");
	    
		IJavaProject iJavaProject = null;
	    
	    ResourcesPlugin f = ResourcesPlugin.getPlugin();
//		IWorkspace ws = ResourcesPlugin.getWorkspace();
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject project = root.getProject("JD6611");
		
		//ASTReader ast = new ASTReader(iJavaProject, null);
		//SystemObject system = ASTReader.getSystemObject();

		//AIF aif = new AIF(system); 
		System.out.println(f);
		
		assertEquals(1, 1);
	}

}
