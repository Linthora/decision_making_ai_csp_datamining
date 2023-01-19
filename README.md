# decision-making AI, CSP and some datamining

This project was initiated for our course on artificial and decision aid to teach us the basics of this field of study. 

From the representation of a general problem to use general implementation of graph path finding algorithm, constraint satisfaction problem algorithm and some datamining algorithm. All of this results in a set of packages each containing the basic tools implemented. 

We then used all of this on a BlockWorld implementation that we made.

However, we went ahead of what was asked of us and implemented a few more algorithms.

If I find the time and motivation about this project I will write proper lab repport for each part, though it might not be all that revelant.

Complementary informations can be found in the documentation, such a description of used algorithm and sometimes proof of given heuristic if necessary.

## 1. Representation

This package provides us all we need to represent a general problem with variables representing some part of our problem with a name and a domain of value. And the constraints that rules it.

Thus, a state or a partial instantiation of a problem must be represented as a map, with variables as keys and their current value. In java that would be `Map<Variable, Object>`.

## 2. Planning

This package contains classical graph algorithm such as:
* DFS
* BFS
* Dijkstra
* A* (AStar)
* BeamSearch

In addition we provide components to let you build you own planner, with interfaces to create your own heuristic as well. Beware, in the case of algorithm using heuristic, you must make sure that you heuristic is admissible for your problem.

*Note1: it would be rather easy to modify BeamSearch and A* to use vectors of heuristic and make an interface to evaluate them in any manner that we see fit.*

*Note2: BeamSearch and A* could be customized so that BeamSearch would directly use an instance of A* with given custom priority queue.*

*Note3: BeamSearch may be fast but this implementation will be slow in the case of large graph with no path to given goal and __BeamSearch won't necerally find the shortest path to your goal__*

## 3. CSP (Constraint Satisfaction Problem)

This package contains basic components to solve a CSP, with method such as:
* Backtracking
* MAC
* MAC with the use of heuristic

Here again we provide the necessary to let you make your own solver, with or without heuristics.

*note: heuristics here aren't proven to be more effective, they just follow an intuition and may or may not be faster, sometimes they can make the search slower so beware* 

## 4. Datamining

Here, we implemented the necessary to build a database with given transactions, represented by sets of boolean variables, and method to mine frequent item sets in our database and then mine the association rule from found frequent item sets.

To mine frequent itemsets we implemented 2 methods:
* APriori
* FPGrowth

*note: my implementation of FPGrowth is working as intended but is far from being perfect as it resulted only from reading papers and blogs describing how it worked*

And for the mining of association rule part, we simply used a brute force like approach to mine all the rules fulfilling our conditions.

## 5. Blockworld

To illustrate the uses all the tools we built we then made a implementation of blockworld, with which we then made demonstrations of those tools and gave conclusions about them as well.

**Note: Any informations on how to use any class used here can be find in the JavaDoc of this project.**

*Credit: Our teacher at the Université de Caen Normandie, for their help in this project and for the given libraries (to tests parts of our project, generate some configurations of blockworld and for the gui illustration of them).*

