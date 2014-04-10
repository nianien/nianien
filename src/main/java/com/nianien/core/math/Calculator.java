package com.nianien.core.math;

import java.util.Stack;

/**
 * 计算数学表达式的工具类, 支持的运算符：'+'、'-'、'*'、'/'、'^'、'('、')'<br>
 * 另参见: {@link com.nianien.core.math.Calculator.Operator}
 * 
 * @author skyfalling
 * 
 */
public class Calculator {

	/**
	 * 计算数学表达式<br>
	 * 
	 * @param expression
	 * @return
	 */
	public static double calculate(String expression) {
		// 表达式去正负号
		expression = expression.replaceAll("(^|\\()(\\+|\\-)", "$1" + 0 + "$2");
		// 存储运算数的栈
		Stack<Double> stackOfVariable = new Stack<Double>();
		// 存储运算符的栈
		Stack<Operator> stackOfOperator = new Stack<Operator>();
		// 存储临时变量结果
		StringBuilder sb = new StringBuilder();
		// 扫描数学表达式
		for (char ch : expression.toCharArray()) {
			if (Operator.isOperator(ch)) {
				// 遇到运算符时, 操作数入栈
				if (sb.length() > 0) {
					stackOfVariable.push(Double.parseDouble(sb.toString()));
					sb.setLength(0);
				}
				// 当前操作符
				Operator current = Operator.valueOf(ch);
				// 与栈顶操作符的优先级比较
				int priority = -1;
				// 如果栈内操作符优先级高, 则优先计算栈内操作
				while (!stackOfOperator.isEmpty()
						&& (priority = stackOfOperator.peek().precede(current)) > 0) {
					Operator last = stackOfOperator.pop();
					double var2 = stackOfVariable.pop();
					double var1 = stackOfVariable.pop();
					// 运算结果作为操作数入栈
					stackOfVariable.push(last.calculate(var1, var2));
				}
				if (priority == 0) {
					stackOfOperator.pop();
				} else {
					stackOfOperator.push(current);
				}
			} else {
				// 累计操作数
				sb.append(ch);
			}
		}
		// 最后的操作数入栈
		if (sb.length() > 0) {
			stackOfVariable.push(Double.parseDouble(sb.toString()));
			sb.setLength(0);
		}
		// 栈中还有操作符, 则继续计算
		while (!stackOfOperator.isEmpty()) {
			Operator lop = stackOfOperator.pop();
			double var2 = stackOfVariable.pop();
			double var1 = stackOfVariable.pop();
			stackOfVariable.push(lop.calculate(var1, var2));
		}
		// 返回计算结果
		return stackOfVariable.pop();
	}

	/**
	 * 判断数学表达式是否合法, 具体判断标准为: <br>
	 * 如果调用方法 {@link #calculate(String)}计算表达式不抛异常, 则视为合法
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isValid(String expression) {
		try {
			calculate(expression);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 运算操作符的枚举类型, 支持以下操作运算: '+'、'-'、'*'、'/'、'^'、'('、')'<br>
	 * 其中操作符'^'为左结合运算, 即a^b^c=a^(b^c)
	 * 
	 * @author skyfalling
	 * 
	 */
	public static enum Operator {
		/**
		 * 加法"+"
		 */
		Add('+') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Rp:
					return 1;
				case Div:
				case Mul:
				case Pow:
				case Lp:
					return -1;
				default:
					throw new NullPointerException();
				}
			}

			@Override
			public double calculate(double var1, double var2) {
				return var1 + var2;
			}
		},
		/**
		 * 减法"-"
		 */
		Sub('-') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Rp:
					return 1;
				case Div:
				case Mul:
				case Pow:
				case Lp:
					return -1;
				default:
					throw new NullPointerException();
				}
			}

			@Override
			public double calculate(double var1, double var2) {
				return var1 - var2;
			}
		},
		/**
		 * 乘法"*"
		 */
		Mul('*') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Div:
				case Mul:
				case Rp:
					return 1;
				case Pow:
				case Lp:
					return -1;
				default:
					throw new NullPointerException();
				}
			}

			@Override
			public double calculate(double var1, double var2) {
				return var1 * var2;
			}
		},
		/**
		 * 除法"/"
		 */
		Div('/') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Div:
				case Mul:
				case Rp:
					return 1;
				case Pow:
				case Lp:
					return -1;
				default:
					throw new NullPointerException();
				}
			}

			@Override
			public double calculate(double var1, double var2) {
				return var1 / var2;
			}
		},
		/**
		 * 幂运算"^"
		 */
		Pow('^') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Div:
				case Mul:
				case Rp:
					return 1;
				case Pow:
				case Lp:
					return -1;
				default:
					throw new NullPointerException();
				}
			}

			@Override
			public double calculate(double var1, double var2) {
				return Math.pow(var1, var2);
			}
		},
		/**
		 * 左括号"("
		 */
		Lp('(') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Div:
				case Mul:
				case Pow:
				case Lp:
					return -1;
				case Rp:
					return 0;
				default:
					throw new NullPointerException();
				}
			}

		},
		/**
		 * 右括号")"
		 */
		Rp(')') {
			@Override
			public int precede(Operator op) {
				switch (op) {
				case Add:
				case Sub:
				case Rp:
				case Div:
				case Mul:
				case Pow:
					return 1;
				case Lp:
					throw new IllegalArgumentException("无效的优先级比较:'"
							+ this.symbol + "', '" + op.symbol + "'");
				default:
					throw new NullPointerException();
				}
			}

		};
		/**
		 * 运算操作符
		 */
		public final char symbol;

		/**
		 * 构造方法, 指定运算符
		 * 
		 * @param ch
		 */
		Operator(char ch) {
			this.symbol = ch;
		}

		/**
		 * 当前操作符与指定操作符比较优先级<br>
		 * 当前操作符为左操作符, 待比较操作符为右操作符, 比较方向从左向右<br>
		 * 
		 * @param op
		 * @return 当前操作符优先级高于指定操作符则返回1, 相等则返回0, 小于则返回-1
		 */
		public abstract int precede(Operator op);

		/**
		 * 利用当前运算符计算操作数, 该方法对于{@link com.nianien.core.math.Calculator.Operator#Lp}和{@link com.nianien.core.math.Calculator.Operator#Rp} 无效
		 * 
		 * @param var1
		 * @param var2
		 * @return
		 */
		public double calculate(double var1, double var2) {
			throw new IllegalArgumentException("运算符: " + symbol + " 不支持该方法");
		}

		/**
		 * 判断是否为以下操作符之一: '+'、'-'、'*'、'/'、'^'、'('、')'
		 * 
		 * @param ch
		 * @return
		 */
		public static boolean isOperator(char ch) {
			switch (ch) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
			case '(':
			case ')':
				return true;
			default:
				return false;
			}
		}

		/**
		 * 根据操作符获取相应的枚举类型, 支持的操作符包括: '+'、'-'、'*'、'/'、'^'、'('、')'
		 * 
		 * @param ch
		 * @return
		 */
		public static Operator valueOf(char ch) {
			switch (ch) {
			case '+':
				return Add;
			case '-':
				return Sub;
			case '*':
				return Mul;
			case '/':
				return Div;
			case '^':
				return Pow;
			case '(':
				return Lp;
			case ')':
				return Rp;
			default:
				throw new IllegalArgumentException("不支持的运算符: " + ch);
			}
		}
	}
}
