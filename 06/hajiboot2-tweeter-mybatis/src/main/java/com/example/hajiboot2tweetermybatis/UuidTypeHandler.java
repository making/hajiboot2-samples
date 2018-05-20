package com.example.hajiboot2tweetermybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class UuidTypeHandler extends BaseTypeHandler<UUID> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UUID uuid,
			JdbcType jdbcType) throws SQLException {
		ps.setString(i, uuid.toString());
	}

	@Override
	public UUID getNullableResult(ResultSet resultSet, String s) throws SQLException {
		return toUUID(resultSet.getString(s));
	}

	@Override
	public UUID getNullableResult(ResultSet resultSet, int i) throws SQLException {
		return toUUID(resultSet.getString(i));
	}

	@Override
	public UUID getNullableResult(CallableStatement callableStatement, int i)
			throws SQLException {
		return toUUID(callableStatement.getString(i));
	}

	private static UUID toUUID(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return UUID.fromString(value);
	}
}