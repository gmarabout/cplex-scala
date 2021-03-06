/*
 * Source file provided under Apache License, Version 2.0, January 2004,
 * http://www.apache.org/licenses/
 * (c) Copyright DecisionBrain SAS 2016,2017
 */

package com.decisionbrain.cplex.cp

import ilog.cp.IloSearchPhase

/**
  * Constructor of class SearchPhase.
  *
  * @param s is the CPO search phase
  * @param model is the constraint programming model
  */
class SearchPhase(s: IloSearchPhase)(implicit model: CpModel) {

  /**
    * Returns the CPLEX search phase.
    *
    * @return the CPLEX search phase
    */
  def getIloSearchPhase(): IloSearchPhase = s

  /**
    * Convert the search phase to a character string.
    * @return
    */
  override def toString: String = s.toString
}

/**
  * Companion object for class SearchPhase
  */
object SearchPhase {

  /**
    * Creates and returns a new search phase.
    *
    * @param s is the CPLEX search phase
    * @param model is the constraint programming model
    * @return a new search phase
    */
  def apply(s: IloSearchPhase)(implicit model: CpModel) = new SearchPhase(s)
}