package fr.imt.acdcgit.facade;


/**
 * This object is part of the facade part. Its purpose is
 * to be a state-storage class passed between the facade's
 * client and the facade. It does not contains or depends
 * on classes implementing business logic. But it can be
 * mapped to existing instances of business logic object
 * by the fact its path attribute is unique (one repo = 
 * one path)
 */
// maybe add accessor for other things ? (diff etc)
public class Repository {
	protected String idPath;
	
	public Repository(String idPath) {
		this.idPath = idPath;
	}
	
	public String getId() {
		return this.idPath;
	}
}
