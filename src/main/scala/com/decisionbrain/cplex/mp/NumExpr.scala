/*
 * Source file provided under Apache License, Version 2.0, January 2004,
 * http://www.apache.org/licenses/
 * (c) Copyright DecisionBrain SAS 2016,2017
 */

package com.decisionbrain.cplex.mp

import ilog.concert.IloNumExpr
import ilog.cplex.IloCplex

/**
  * Class for numeric expressions
  *
  * @param expr  is the numeric expression
  * @param model is the mathematical programming model
  */
class NumExpr(val expr: IloNumExpr)(implicit model: MpModel) {

  /**
    * Return the mathematical programming model the numeric expression belongs to.
    *
    * @return the mathematical programming model
    */
  def getMpModel(): MpModel = model

  /**
    * Return the mathematical programming model the numeric expression belongs to.
    *
    * @return the mathematical programming model
    */
  def getIloCplex(): IloCplex = model.cplex

  /**
    * Return the CPLEX numeric expression.
    *
    * @return the numeric expression
    */
  def getIloNumExpr: IloNumExpr = expr

  /**
    * Creates and returns an expression representing the sum of this numeric expression and another numeric
    * expression.
    *
    * @param y is the other numeric expression
    * @return a numeric expression representing the sum <em>x + y</em>
    */
  def +(y: NumExpr): NumExpr = {
    NumExpr(model.cplex.sum(expr, y.getIloNumExpr))
  }

  /**
    * Creates and returns an expression representing the sum of this numeric expression and a numeric constant.
    *
    * @param v is the numeric constant
    * @return a numeric expression representing the sum <em>x + v</em>
    */
  def +(v: Double): NumExpr = {
    NumExpr(model.cplex.sum(expr, v))
  }

  /**
    * Creates and returns an expression representing the sum of the numeric expressions <em>x</em> and <em>that</em>
    *
    * @param y is the numeric expression to add
    * @return a numeric expression representing the sum <em>x - y</em>
    */
  def -(y: NumExpr): NumExpr = {
    NumExpr(model.cplex.diff(expr, y.getIloNumExpr))
  }

  /**
    * Creates and returns an expression representing the diff of this numeric expression and a numeric constant.
    *
    * @param v is the numeric constant
    * @return a numeric expression representing the sum <em>x - v</em>
    */
  def -(v: Double): NumExpr = {
    NumExpr(model.cplex.diff(expr, v))
  }

  /**
    * Create and returns an expression that is the negation of this expression.
    *
    * @return the negation of this expression
    */
  def unary_- : NumExpr = NumExpr(model.cplex.negative(expr))

  /**
    * Creates and returns an expression representing the product of the expression x and the value v.
    *
    * @param v is the value to use in the product.
    * @return a numeric expression representing the product <em>e1 * v</em>.
    */
  def *(v: Double): NumExpr = {
    NumExpr(model.cplex.prod(expr, v))
  }

  /**
    * Creates and returns an expression representing the product of the expression x and another expression y.
    *
    * @param expr is the value to use in the product.
    * @return a numeric expression representing the product <em>e1 * v</em>.
    */
  def *(expr: NumExpr): NumExpr = {
    NumExpr(model.cplex.prod(this.expr, expr.getIloNumExpr))
  }

  /**
    * Creates and returns an instance of IloRange that represents the constraint <em>expr >= v</em>.
    *
    * @param value is the lower bound of the new greater-than-or-equal-to constraint.
    * @return a new <em>IloRange</em> instance that represents the constraint <em>x >= value</em>.
    */
  def >=(value: Double): RangeConstraint = {
    RangeConstraint(model.cplex.ge(expr, value))
  }

  /**
    * Creates and returns an instance of IloConstraint that represents the constraint <em>expr1 >= expr2</em>.
    *
    * @param e is the right-hand side expression of the greater than or equal constraint
    * @return a new constraint
    */
  def >=(e: NumExpr): Constraint = {
    Constraint(model.cplex.ge(expr, e.getIloNumExpr))
  }

  /**
    * Creates and returns an instance of IloRange that represents the constraint <em>expr <= v</em>.
    *
    * @param value is the upper bound of the new less-than-or-equal-to constraint.
    * @return a new <em>IloRange</em> instance that represents the constraint <em>x <= value</em>.
    */
  def <=(value: Double): RangeConstraint = {
    RangeConstraint(model.cplex.le(expr, value))
  }

  /**
    * Creates and returns an instance of IloRange that represents the constraint <em>expr1 <= expr2</em>.
    *
    * @param e is the right-handside expression of the new less-than-or-equal-to constraint.
    * @return a new <em>IloConstraint</em>
    */
  def <=(e: NumExpr): Constraint = {
    Constraint(model.cplex.le(expr, e.expr))
  }

  /**
    * Creates and returns an instance of IloRange that represents the constraint <em>expr == value</em>.
    *
    * @param value is the numeric value of the new equal-to constraint.
    * @return a new <em>IloRange</em> instance that represents the constraint <em>expr == value</em>.
    */
  def ==(value: Double): RangeConstraint = {
    RangeConstraint(model.cplex.eq(expr, value))
  }

  /**
    * Creates and returns an instance of IloRange that represents the constraint <em>expr1 == expr2</em>.
    *
    * @param e is the numeric expression of the new equal-to constraint.
    * @return a new <em>IloConstraint</em> instance that represents the constraint <em>expr1 == expr2</em>.
    */
  def ==(e: NumExpr): Constraint = {
    Constraint(model.cplex.eq(expr, e.expr))
  }

  /**
    * Return a character string that represents the numeric expression.
    *
    * @return a character string
    */
  override def toString: String = expr.toString()
}

object NumExpr {
  /**
    * Converts a CPLEX numeric expression to a numeric expression.
    *
    * @param e is the CPLEX numeric expression
    * @param model is the mathematical programming model
    * @return a numeric expression
    */
  def apply(e: IloNumExpr)(implicit model: MpModel) = new NumExpr(e)

  /**
    * Implicit Conversion of an object of type Double to a NumConstant i.e. a constant numeric expression.
    *
    * @param value is the value of the constant numeric expression
    * @param model is the mathermatical programming model
    */
  implicit class LinearNumExpr(value: Double)(implicit model: MpModel)
    extends NumExpr(model.cplex.linearNumExpr(value)) {
  }

  /**
    * Implicit conversion of an integer to a constant numeric expression.
    *
    * @param value is the integer value
    * @param model is the mathematical programming model
    */
  implicit class LinearIntExpr(val value: Int)(implicit model: MpModel)
    extends NumExpr(model.cplex.linearIntExpr(value)) {
  }

}