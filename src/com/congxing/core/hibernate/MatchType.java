package com.congxing.core.hibernate;


public enum MatchType {	
	n,//空
	nn,//非空
	eq,//相等
	ne,//非等
	k,//like
	nk,//not like
	lt,//<
	le,//<=
	gt,//>
	ge,//>=
	in,//in
	nin,//in
	sql;//sql
}
