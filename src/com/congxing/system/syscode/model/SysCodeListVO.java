package com.congxing.system.syscode.model;

import com.congxing.core.web.struts2.BaseListVO;

@SuppressWarnings("serial")
public class SysCodeListVO extends BaseListVO{
	
	private String _seq_type;
	private String _sk_type;
	private String _sk_name;
	private String _seq_name;
	private String _seq_kind;
	private String _sne_kind;
	private String _sk_kind;
	private String _sk_kindname;
	private String _seq_kindname;
	
	
	public String get_seq_type() {
		return _seq_type;
	}
	public void set_seq_type(String seqType) {
		_seq_type = seqType;
	}
	public String get_seq_kind() {
		return _seq_kind;
	}
	public void set_seq_kind(String seqKind) {
		_seq_kind = seqKind;
	}
	public String get_sne_kind() {
		return _sne_kind;
	}
	public void set_sne_kind(String sneKind) {
		_sne_kind = sneKind;
	}
	public String get_sk_name() {
		return _sk_name;
	}
	public void set_sk_name(String skName) {
		_sk_name = skName;
	}
	public String get_sk_kindname() {
		return _sk_kindname;
	}
	public void set_sk_kindname(String skKindname) {
		_sk_kindname = skKindname;
	}
	public String get_seq_name() {
		return _seq_name;
	}
	public void set_seq_name(String seqName) {
		_seq_name = seqName;
	}
	public String get_seq_kindname() {
		return _seq_kindname;
	}
	public void set_seq_kindname(String _seq_kindname) {
		this._seq_kindname = _seq_kindname;
	}
	public String get_sk_kind() {
		return _sk_kind;
	}
	public void set_sk_kind(String skKind) {
		_sk_kind = skKind;
	}
	public String get_sk_type() {
		return _sk_type;
	}
	public void set_sk_type(String skType) {
		_sk_type = skType;
	}
	
	
}
