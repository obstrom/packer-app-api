# Notes

**Goal:** A Java module that can pack a given list of volumetric items into a set of boxes, picking the most optimal box(es) for the operation in 3D space.

Should also take different weighted parameters into consideration. For instance; spreading the items over to smaller boxes might be better than placing them all in a single box.

### Requirements
* Take a list of packable items as input. Items will have volume and perhaps optionally also weight.
* Have a database, or take as an input, a collection of boxes (i.e containers to fill).
* Find the most optimal solution given one of several strategies.
* Take some optional parameters into consideration, like:
  * Keeping within an accepted volume for each packed box
  * Keeping within an accepted weight range for each packed box

### Optional features
* Return not only the optimal solution, but rather a list of several solutions ranked and sorted to give options.
* Save the input configuration as a hashable value together with the result in a database, so lookups can be made against already calculated solutions.
* An online API to query with a web view
* Generate a report sheet to make the task and solution more easily human-readable.

### Considerations
* Look into already existing algorithms and strategies.
* Consider what heuristics are possible for narrowing down searches.

## Heuristic approach

Listing some possible heuristics to optimize the process.

### Optimize for a single bin approach

1. Filter out any bins whose volumes fall short of the total volume of the task.
2. Filter out any bins whose dimensions won't hold the dimensions of any given item in the task (e.g. a long item for a
   cube box).
3. Sorts the items in descending order of size, attempting to place the bigger items first.

### Post sorting adjustments

1. Favor solutions that place heavier items to the bottom when stacking if deciding between solutions.

### 6/4 2022 Notes

Use the LAFF algorithm from the paper "An Efficient Algorithm for 3D Rectangular Box Packing". To work around the
algorithm finding the optimal stacking instead of the optimal container try the following:

* Derive the width and depth parameters for the first steps of the algorithm from the Bin instead of the longest times
* You could get the optimal width and depth first to remove any Bin with a too small of a footprint
* And if the packing blows the height, just skip that Bin or the solution