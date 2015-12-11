package lildoop.mapReduce.enums;

public enum ConditionOperator {
	EQUAL
	{
		@Override
		public boolean checkCondition(String value, String param) {
			return param.equals(value);
		}
	},
	
	NOT_EQUAL
	{
		public boolean checkCondition(String value, String param) {
			return !param.equals(value);
		}
	},
	
	LESS_THAN
	{
		@Override
		public boolean checkCondition(String value, String param) {
			if(value.matches("\\d") && param.matches("\\d")) {
				int valInt = Integer.parseInt(value);
				int paramInt = Integer.parseInt(param);
				return valInt < paramInt;
			} else {
				throw new IllegalArgumentException("Value or param was not an int");
			}
		}
	},
	GREATER_THAN
	{
		@Override
		public boolean checkCondition(String value, String param) {
			if(value.matches("\\d") && param.matches("\\d")) {
				int valInt = Integer.parseInt(value);
				int paramInt = Integer.parseInt(param);
				return valInt > paramInt;
			} else {
				throw new IllegalArgumentException("Value or param was not an int");
			}
		}
	},
	LESS_THAN_OR_EQUAL
	{
		@Override
		public boolean checkCondition(String value, String param) {
			if(value.matches("\\d") && param.matches("\\d")) {
				int valInt = Integer.parseInt(value);
				int paramInt = Integer.parseInt(param);
				return valInt <= paramInt;
			} else {
				throw new IllegalArgumentException("Value or param was not an int");
			}
		}
	},
	GREATER_THAN_OR_EQUAL
	{
		@Override
		public boolean checkCondition(String value, String param) {
			if(value.matches("\\d") && param.matches("\\d")) {
				int valInt = Integer.parseInt(value);
				int paramInt = Integer.parseInt(param);
				return valInt >= paramInt;
			} else {
				throw new IllegalArgumentException("Value or param was not an int");
			}
		}
	},
	NO_COND {
		@Override
		public boolean checkCondition(String value, String param) {
			return true;
		}
	} ;	
	public abstract boolean checkCondition(String value, String param);
}
