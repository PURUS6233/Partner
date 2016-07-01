package ua.partner.suzuki.domain;

import java.util.ArrayList;
import java.util.Collection;

public class OBMGroup {
	
	public OBMGroup(Collection<OBM> obms){
		setObmsGroup(obms);
	}
	
	public OBMGroup(OBM obm){
		setObmsGroup(obm);
	}
	
	private Collection<OBM> obmsGroup;
	
	public Collection<OBM> getObmsGroup() {
		return obmsGroup;
	}

	public void setObmsGroup(Collection<OBM> obmsGroup) {
		this.obmsGroup = addOBMs(obmsGroup);
	}
	
	public void setObmsGroup(OBM obm) {
		this.obmsGroup = addOBM(obm);
	}
	
	public Collection<OBM> addOBMs(Collection<OBM> obms){
		Collection<OBM> source = new ArrayList<OBM>();
		source.addAll(obms);
		return source;
	}
	
	public Collection<OBM> addOBM(OBM obm){
		Collection<OBM> source = new ArrayList<OBM>();
		source.add(obm);
		return source;
	}
}