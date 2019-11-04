package fr.imt.acdcgit.facade;

import org.eclipse.jgit.transport.CredentialsProvider;

public abstract class AbstractCoreFacade implements CoreInterface {
	private String path;
	private boolean isIndex;
	CredentialsProvider credsProvider;
	/**
	 * Facade constructor prototype.
	 * @param path can be both a searchpath or an index file containing list of repositories
	 * @param isIndex tells if the path is an index file or a searchpath
	 * @param cp object extending the abstract CredentialsProvider class. Will be called when credentials are needed.
	 */
	public AbstractCoreFacade(String path, boolean isIndex, CredentialsProvider cp) {
		this.path = path;
		this.isIndex = isIndex;
		this.credsProvider = cp;
	}
}
