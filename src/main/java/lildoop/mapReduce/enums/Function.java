package lildoop.mapReduce.enums;


import org.json.JSONObject;

import lildoop.mapReduce.models.QueryResult;

public enum Function {
	COUNT
	{
		@Override
		public QueryResult doFunction(String[][] data, ConditionOperator cond, String param) {
			int count = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i][1], param)) {
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
		public QueryResult doFunction(String[][] data, ConditionOperator cond, String param) {
			int sum = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i][1], param)) {
					sum+= Integer.parseInt(data[i][0]);
				}
			}
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + sum + "}";
			return new QueryResult(new JSONObject(json));
		}
	},	
	AVG
	{
		@Override
		public QueryResult doFunction(String[][] data, ConditionOperator cond, String param) {
			int count = 0;
			int sum = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i][1], param)) {
					count++;
					sum+= Integer.parseInt(data[i][0]);
					System.out.println(sum);
				}
			}
			int value = sum /count;
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + value + "}";
			return new QueryResult(new JSONObject(json));
		}
	};
	
	public abstract QueryResult doFunction(String[][] data, ConditionOperator cond, String param);
}
