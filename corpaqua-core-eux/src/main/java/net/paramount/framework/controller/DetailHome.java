/**
 * 
 */
package net.paramount.framework.controller;

import lombok.Getter;
import lombok.Setter;
import net.paramount.framework.model.FilterBase;

/**
 * @author bqduc
 *
 */
public abstract class DetailHome<E> extends Home <E, FilterBase> {
	private static final long serialVersionUID = 9111398682757783220L;

	protected static final String PAGE_POSTFIX_DETAIL = "Detail";
	protected static final String PAGE_POSTFIX_BROWSE = "Browse";

	@Setter
	@Getter
	private Boolean createOther;

	protected abstract void doInit();

	public void init() {
		doInit();
  }
}
