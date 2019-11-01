package fr.imt.acdcgit.features;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

class RepoDiffFeatureFactory implements FeatureFactoryInterface<RepoDiffFeature> {
	public RepoDiffFeature getInstance(Git repoUsedByImplClass, CredentialsProvider cp) {
		return new RepoDiffFeature(repoUsedByImplClass, cp);
	}
}

// RevWalk = iteration over commits
// RevCommit = representation of a commit
// TreeWalk = list files within a commit
// see https://doc.nuxeo.com/blog/jgit-example/
// TODO this class is a stub !
public class RepoDiffFeature extends RepoStatusFeature {
	public static final FeatureFactoryInterface<RepoDiffFeature> FACTORY = new RepoDiffFeatureFactory();

	public RepoDiffFeature(Git repo, CredentialsProvider cp) {
		super(repo, cp);
	}

	/**
	 * Get the Ref of the upstream branch for the main local branch. Main local
	 * branch is determined by the getMainBranch method.
	 * 
	 * @return nullable, ref of the main branch
	 */
	protected Ref getMainBranchUpstream() throws GitAPIException, Exception {
		String remoteTrackingBranch = this.getMainBranchTrackingStatus().getRemoteTrackingBranch();
		Ref remoteTrackingBranchRef = this.repo.getRepository().exactRef(remoteTrackingBranch);
		return remoteTrackingBranchRef;
	}

	/**
	 * Get the tree parser for a given ref, using the stored repo
	 * 
	 * @param ref Ref from which you get the tree to parse
	 * @return parser for given ref
	 */
	protected AbstractTreeIterator getTreeParser(Ref ref) throws Exception {
		RevWalk walk = new RevWalk(this.repo.getRepository());
		// get the commit associated to ref
		RevCommit commit = walk.parseCommit(ref.getObjectId());
		// get the tree (tree of objects, blob, files) associated to commit
		RevTree tree = walk.parseTree(commit.getTree().getId());
		// for parsing the git tree
		CanonicalTreeParser parser = new CanonicalTreeParser();
		ObjectReader reader = this.repo.getRepository().newObjectReader();
		parser.reset(reader, tree.getId());
		walk.dispose();
		walk.close();

		return parser;
	}

	public String getDiff() throws TransportException, GitAPIException, Exception {
		// first, fetch remote as we are doing a diff between origin branch and our
		// branch
		fetchForMainBranch();
		// get the list of diff entries
		List<DiffEntry> diffList = this.repo.diff()
				.setOldTree(getTreeParser(this.getMainBranch()))
				.setNewTree(getTreeParser(this.getMainBranchUpstream()))
				.call();
		StringBuffer out = new StringBuffer();
		for (DiffEntry diff : diffList) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // output stream used
			DiffFormatter formatter = new DiffFormatter(outputStream);
			formatter.setRepository(this.repo.getRepository());
			formatter.format(diff);
			formatter.close();
			out.append(new String(outputStream.toByteArray(), Charset.defaultCharset()));
		}
		return out.toString();
	}
}
