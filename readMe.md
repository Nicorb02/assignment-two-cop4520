# Labyrinth and Crystal Vase Problems

## Approach:

This approach takes advantage of the multi-threading capabilities in Java. For solving the labyrinth problem, we are using atomic integers to ensure proper incrementing of guest visits which is updated by each thread and then terminating once all threads have finished the maze. For solving the crystal vase showroom problem, I have chose the queue method as it will ensure all the guests will enter the room.

## Showroom Advantages and Disadvantages:
Open Door Strategy: Has the advantage that a guest can enter the showroom whenever. Has the disadvantage of crowding at the door with no guarantee of being able to enter.

Sign Strategy: Has the advantage of avoiding crowds since the guests can see the sign. Has the disadvantage that guests may not accurately update the sign.

Queue Strategy: Has the advantage that every guest will enter the showroom guaranteed. Has the disadvantage that guests must wait in a queue.

## Correctness:

To ensure program accuracy, print statements to the console are happening at each important step. I ensure that each thread did in fact accomplish its task before progrm termination.
