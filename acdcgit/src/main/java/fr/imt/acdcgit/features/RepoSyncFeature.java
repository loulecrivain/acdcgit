package fr.imt.acdcgit.features;

import java.io.CharArrayWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.CanceledException;

class RepoSyncFeatureFactory implements FeatureFactoryInterface<RepoSyncFeature> {
	public RepoSyncFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new RepoSyncFeature(repoUsedByImplClass, cp);
	}
}

public class RepoSyncFeature extends RepoDiffFeature {
	public static final FeatureFactoryInterface<RepoSyncFeature> FACTORY = new RepoSyncFeatureFactory();
	// output stream used for progress monitoring
	// protected ByteArrayOutputStream progressOutputStream;
	// writer used for progress monitoring
	protected CharArrayWriter progressOutputWriter;
	protected ProgressMonitor progressMonitor;

	public RepoSyncFeature(Git repo, CredentialsProvider cp) {
		super(repo,cp);
		// progressOutputStream = new ByteArrayOutputStream();
		progressOutputWriter = new CharArrayWriter();
	}

	protected RefUpdate.Result checkoutMainBranch() throws Exception {
		return repo.getRepository().updateRef(Constants.HEAD).link(getMainBranchStr()); 
	}
	
	protected boolean pushResultsOK(Iterable<PushResult> pushResults) {
		boolean ok = true;
		if (pushResults != null) {
			for(PushResult pushResult: pushResults) {
				for (RemoteRefUpdate rru: pushResult.getRemoteUpdates()) {
					RemoteRefUpdate.Status status = rru.getStatus();
					if(!(status.equals(RemoteRefUpdate.Status.OK) ||
					   status.equals(RemoteRefUpdate.Status.UP_TO_DATE))) {
						ok = false;
					}
				}
			}
		}
		return ok;
	}
	
	protected boolean launchPush(boolean pushWithCreds) throws Exception {
		checkoutMainBranch();
		Iterable<PushResult> pushResults;
		if(pushWithCreds) {
			pushResults = repo.push().setProgressMonitor(progressMonitor).setCredentialsProvider(credsProvider).call();
			return pushResultsOK(pushResults);
		} else {
			try {
				pushResults = repo.push().setProgressMonitor(progressMonitor).call();
				return pushResultsOK(pushResults);
			} catch (TransportException te) {
				// do nothing
			} catch (CanceledException ce) {
				// do nothing
			}
		}
		return false;
	}
	
	protected boolean launchPull(boolean pullWithCreds) throws Exception {
		// first checkout main branch
		checkoutMainBranch();
		// then launch pull with or without creds, if without, try
		// to catch exception
		PullResult pullResult;
		if (pullWithCreds) {
			pullResult = repo.pull().setProgressMonitor(progressMonitor).setCredentialsProvider(credsProvider).call();
			if (pullResult != null) {return pullResult.getMergeResult().getMergeStatus().isSuccessful();}
		} else {
			try {
				pullResult = repo.pull().setProgressMonitor(progressMonitor).call();
				if (pullResult != null) {return pullResult.getMergeResult().getMergeStatus().isSuccessful();}
			} catch (TransportException te) {
				// do nothing
			} catch (CanceledException ce) {
				// do nothing
			}
		}
		return false;
	}

	protected boolean launchSync(boolean isInteractive) throws Exception {
		boolean ok = true;
		progressOutputWriter.reset();
		// gc previous reference if any
		progressMonitor = new TextProgressMonitor(progressOutputWriter);
		// launch sync by status, pull first
		RepoStatusFeature.RepoState state = this.getState(true);
		if(isBehind(state)) {
			if(!this.launchPull(isInteractive)) {
				ok = false;
			}
		}
		// then push if needed
		if (isAhead(state)) {
			if(!this.launchPush(isInteractive)) { // push failed
				ok = false;
			}
		}
		return ok;
	}

	public boolean launchAutoSync() throws Exception {
		return launchSync(false); // launch sync not interactively
	}

	public boolean launchManualSync() throws Exception {
		return launchSync(true); // launch sync interactively 
	}

	String getSyncCompletion() {
		if(progressOutputWriter != null) {
			return progressOutputWriter.toString();
		}
		return "unknown";
	}
}
