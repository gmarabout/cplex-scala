/*
 * Source file provided under Apache License, Version 2.0, January 2004,
 * http://www.apache.org/licenses/
 * (c) Copyright DecisionBrain SAS 2016,2017
 */

package com.decisionbrain.cplex.cp

import com.decisionbrain.cplex.Addable

import ilog.concert.{IloAddable, IloConstraint}

/**
  * Class for constraints.
  *
  * @param c is the CPLEX constraint
  */
class Constraint(c: IloConstraint)(implicit model: CpModel) extends IntExpr(c) with Addable {

  /**
    * Return the CPLEX constraint.
    *
    * @return the CPLEX constraint
    */
  def getIloConstraint(): IloConstraint = c

  /**
    * Returns the name of model addable object.
    *
    * @return the name of the object
    */
  override def getName: Option[String] = Option(c.getName())

  /**
    * Set the name of the model addable object.
    *
    * @param name is the name of the object
    */
  override def setName(name: String): Unit = c.setName(name)

  /**
    * Returns the CPLEX addable object.
    *
    * @return the CPLEX addable object
    */
  override def getIloAddable(): IloAddable = c

  /**
    * Creates and return a logical or constraint between two constraints.
    *
    * @param ct is the other constraint of the logical or
    * @return a new constraint
    */
  def ||(ct: Constraint): Constraint = {
    Constraint(model.cp.or(this.getIloConstraint(), ct.getIloConstraint))
  }

  /**
    * Creates and return a logical and constraint between two constraints.
    *
    * @param ct is the other constraint of the logical and
    * @return a new constraint
    */
  def &&(ct: Constraint): Constraint = {
    Constraint(model.cp.and(this.getIloConstraint(), ct.getIloConstraint))
  }

  /**
    * Creates and returns a logical not constraint.
    *
    * @return a new constraint
    */
  def unary_!(): Constraint = {
    Constraint(model.cp.not(this.getIloConstraint()))
  }

  /**
    * Creates and returns a logical imply constraint.
    *
    * @param ct is the other constraint
    * @return a new constraint
    */
  def <=(ct: Constraint): Constraint = {
    Constraint(model.cp.ifThen(this.getIloConstraint(), ct.getIloConstraint))
  }

  /**
    * Creates and returns a logical if-then-else constraint.
    *
    * @param ct1 is the 'then' constraint
    * @param ct2 is the 'else' constraint
    * @return a new constraint
    */
  def ?(ct1: Constraint, ct2: Constraint): Constraint = {
    Constraint(model.cp.ifThenElse(this.getIloConstraint(), ct1.getIloConstraint, ct2.getIloConstraint))
  }
}

object Constraint {

  /**
    * Converts a CPLX constraint to a constraint
    *
    * @param c is the CPLEX constraint
    * @return a constraint
    */
  def apply(c: IloConstraint)(implicit model: CpModel): Constraint = new Constraint(c)

  /**
    * Returns a constraint that is always true.
    *
    * @param model is the constraint programming model
    * @return a new constraint that is always true
    */
  def TRUE(implicit model: CpModel): Constraint = model.constraint(true)

  /**
    * Returns a constraint that is always false.
    *
    * @param model is the constraint programming model
    * @return a new constraint that is always false
    */
  def FALSE(implicit model: CpModel): Constraint = model.constraint(false)
}
