package com.dianping.bee.engine.spi;

import com.dianping.bee.engine.spi.meta.RowSet;

public interface Statement {
	public int getParameterSize();

	public int getColumnSize();

	public RowSet query();
}
