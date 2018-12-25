package com.jay.kerrigan.master.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import com.jay.kerrigan.common.entity.table.Token;

@Mapper
public interface TokenMapper {

	@SelectProvider(type = TokenProvider.class, method = "getByTokenAndUserName")
	Token getByUserNameAndHost(@Param("userName") String userName, @Param("host") String host);

	@InsertProvider(type = TokenProvider.class, method = "insertToken")
	int insertToken(Token token);

	@UpdateProvider(type = TokenProvider.class, method = "updateToken")
	int updateToken(Token token);

	class TokenProvider {

		public String getByTokenAndUserName(String userName, String host) {
			return new SQL().SELECT("*").FROM(Token.getTableName()).WHERE("user_name = #{userName}").AND()
					.WHERE("host = #{host}").toString();
		}

		public String insertToken(Token token) {
			return new SQL() {
				{
					INSERT_INTO(Token.getTableName());
					VALUES("token_id", "#{tokenId}");
					VALUES("host", "#{host}");
					VALUES("user_name", "#{userName}");
					VALUES("create_date", "#{createDate}");
					VALUES("update_date", "#{updateDate}");
				}
			}.toString();
		}

		public String updateToken(Token token) {
			return new SQL() {
				{
					UPDATE(Token.getTableName());
					SET("token_id=#{tokenId}");
					SET("update_date=#{updateDate}");
					WHERE("host=#{host}", "user_name=#{userName}");
				}
			}.toString();
		}

	}

}
