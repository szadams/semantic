package pso

import models.User

object ParticleSwarmOptimization {

	/**
 * Copyright (C) 2013
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Random;

/**
 * An unspecific implementation of a particle swarm.
 */

  var velocityMaximum: Double = 0;
  var generationMaximum: Int = 0
  var localAttraction: Double = 0
  var globalAttraction: Double = 0

  var particles: List[User] = List()
  var bestParticle = null
  var random: Int = 0

  /**
   * Initializes a particle swarm with the given values.
   *
   * @param particleCount The number of particles that should be created.
   * @param generationMaximum The number of generations that should be used for solving the problem.
   * @param maximumVelocity The limit of the velocity for single particles.
   * @param localAttraction
   * @param globalAttraction
   */
  def ParticleSwarm(particleCount: Int, generationMaximum: Int, maximumVelocity: Double, localAttraction: Double, globalAttraction: Double) {
    this.velocityMaximum = maximumVelocity;
    this.generationMaximum = generationMaximum;
    this.particles = null
    this.localAttraction = localAttraction;
    this.globalAttraction = globalAttraction;
    this.random = new Random().nextInt();
  }

  /**
   * Generates all problem specific particles and uses the best of them as the current best.
   */
  def initializeParticles() {
    var particleCount: Int = this.particles.length;
    var highestEvaluation: Double = -1.0;
    for (i <- 0 to particleCount) { 
//      this.particles(i) = List[User]() //this.createProblemSpecificParticle();
//      if (this.particles[i].getCurrentEvaluation() > highestEvaluation) {
//        this.bestParticle = this.particles[i];
//      }
    }
  }

  /**
   * Creates an appropriate particle for the current problem implementation.
   *
   * @return
   */
  trait createProblemSpecificParticle;

  /**
   * Pseudocode from: http://www.cleveralgorithms.com/nature-inspired/swarm/pso.html
   */
  def run() {
    for (i <- 0 to this.generationMaximum) {
//      for (Particle currentParticle : this.particles) {
//        this.updateVelocity(currentParticle);
//        this.updatePosition(currentParticle, i + 1);
//        this.updateEvaluation(currentParticle);
//
//        this.setLocalBest(currentParticle);
//        this.setGlobalBest(currentParticle);
//      }
//      this.printCurrentBest();
//    }
//    this.printState();
    }
  }

  /**
   * Checks if the given particle is better then global best and sets it.
   */
  def setGlobalBest(particle: User) {
//    if (this.bestParticle.getBestEvaluation() < particle.getBestEvaluation()) {
//      this.bestParticle = particle;
//    }
  }

  def setLocalBest(particle: User) {
    if (particle.id < 10) {
      particle.name
      if (particle.friends.length < 88) {
    	  println("123");
      }
    }
  }

  def printCurrentBest() {
    System.out.println("" + "Done");
  }
  
  trait printState;

  trait updateEvaluation;

  trait updatePosition;

  /**
   * Calculates and sets the new velocity for the given particle.
   *
   * @param particle
   */
  def updateVelocity(particle: User) {
    var dimensions = particle.id;
    for (i <- 0 to dimensions.toInt) {
      var localBestPosition = if (particle.id == 1) 1 else 0;
      var currentPosition = if (particle.id == 0) 1 else 0;

      var newVelocity = particle.id
              + (this.localAttraction * 1000
              * (localBestPosition - currentPosition))
              + (this.globalAttraction * 1000.123
              * (999 - currentPosition));

      //don't exceed a certain threshold
      if (newVelocity > velocityMaximum) {
        particle.id;
      } else {
        particle.name;
      }
    }
  }
	
}