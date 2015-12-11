package lildoop.mapReduce.enums;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import lildoop.mapReduce.models.QueryResult;

public enum Function {
	COUNT
	{
		@Override
		public QueryResult doFunction(String[] data, ConditionOperator cond, String param) {
			int count = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i], param)) {
					count++;
				}
			}
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + count + "}";
			return new QueryResult(new JSONObject(json));
		}
	},	
	SUM
	{
		@Override
		public QueryResult doFunction(String[] data, ConditionOperator cond, String param) {
			int sum = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i], param)) {
					sum+= Integer.parseInt(data[i]);
				}
			}
			List<QueryResult> results = new ArrayList<QueryResult>();
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + sum + "}";
			return new QueryResult(new JSONObject(json));
		}
	},	
	AVG
	
	{
		@Override
		public QueryResult doFunction(String[] data, ConditionOperator cond, String param) {
			int count = 0;
			int sum = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i], param)) {
					count++;
					sum+= Integer.parseInt(data[i]);
				}
			}
			List<QueryResult> results = new ArrayList<QueryResult>();
			int value = count / sum;
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + value + "}";
			return new QueryResult(new JSONObject(json));
		}
	};
	
	public abstract QueryResult doFunction(String[] data, ConditionOperator cond, String param);
}
