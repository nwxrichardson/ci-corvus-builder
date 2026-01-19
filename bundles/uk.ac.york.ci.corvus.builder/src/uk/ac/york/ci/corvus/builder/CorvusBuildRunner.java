package uk.ac.york.ci.corvus.builder;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.ui.PlatformUI;

import org.eclipse.swt.widgets.Display;
import uk.ac.york.corvus.containers.CorvusGenContainer;
import uk.ac.york.corvus.jobs.GenMatchOdesignFromOdesign;

public class CorvusBuildRunner implements IApplication {

	private IProgressMonitor progressMonitor;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		context.applicationRunning();
		Map<String, Object> contextArguments = context.getArguments();
		progressMonitor = new NullProgressMonitor();
		run();
		return null;
	}

	private void run() {
		try {
			IProjectDescription inDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path("C:/Users/nr823/git/psl-corvus-paper/psl.design/.project"));
			IProject inProject = ResourcesPlugin.getWorkspace().getRoot().getProject(inDescription.getName());
			if (!inProject.exists())	inProject.create(inDescription, null);
			
			inProject.open(null);
			IPath inPath = new Path("/description/psl.odesign");
			IFile inFile = inProject.getFile(inPath);
			
			IProjectDescription outDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path("C:/Users/nr823/git/ci-corvus-builder/util/uk.ac.york.diff.representation/.project"));
			IProject outProject = ResourcesPlugin.getWorkspace().getRoot().getProject(outDescription.getName());
			if (!outProject.exists())	outProject.create(outDescription, null);
			
			outProject.open(null);
			IPath outPath = new Path("/description/compare.odesign");
			IFile outFile = outProject.getFile(outPath);
			
			GenMatchOdesignFromOdesign genMatch = new GenMatchOdesignFromOdesign(
					URI.createPlatformResourceURI(inFile.getFullPath().toOSString(), true),
					URI.createPlatformResourceURI(outFile.getFullPath().toOSString(), true));
			genMatch.schedule();
			genMatch.join();
			genMatch.saveOutput();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	
		

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
