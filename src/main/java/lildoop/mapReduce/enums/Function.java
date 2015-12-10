package lildoop.mapReduce.enums;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import lildoop.mapReduce.models.QueryResult;

public enum Function {
	COUNT
	{
		@Override
		public List<QueryResult> doFunction(String[] data, ConditionOperator cond, String param) {
			int count = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i], param)) {
					count++;
				}
			}
			List<QueryResult> results = new ArrayList<QueryResult>();
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + count + "}";
			results.add(new QueryResult(new JSONObject(json)));
			return results;
		}
	},	
	SUM
	{
		@Override
		public List<QueryResult> doFunction(String[] data, ConditionOperator cond, String param) {
			int sum = 0;
			for(int i = 0; i < data.length; i++) {
				if(cond.checkCondition(data[i], param)) {
					sum+= Integer.parseInt(data[i]);
				}
			}
			List<QueryResult> results = new ArrayList<QueryResult>();
			String json = "{function:" + this.name() + ",param:" + param + ",value:" + sum + "}";
			results.add(new QueryResult(new JSONObject(json)));
			return results;
		}
	},	
	AVG
	
	{
		@Override
		public List<QueryResult> doFunction(String[] data, ConditionOperator cond, String param) {
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
			results.add(new QueryResult(new JSONObject(json)));
			return results;
		}
	};
	
	public abstract List<QueryResult> doFunction(String[] data, ConditionOperator cond, String param);
}
