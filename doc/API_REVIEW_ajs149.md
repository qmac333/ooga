## API Review

### Comments About My API From My Partner
#### Part 1

1. How does your API encapsulate your implementation decisions?
    * It calls private methods which the caller cannot see.
2. What abstractions is your API built on?
    * All displays have a simple update method with no parameters or return values.
3. What about your API's design is intended to be flexible?
    * Update can do whatever it needs to do for different displays.
4. What exceptions (error cases) might occur in your API and how are they addressed?
    * None.

#### Part 2

1. How do you justify that your API is easy to learn?
    * It doesn't need parameters and doesn't return anything. It is also very clearly named.
2. How do you justify that API leads to readable code?
    * It performs 2 different actions that corresponds with the display.
3. How do you justify that API is hard to misuse?
    * It doesn't update model (only reads). It does not take any parameters.
4. Why do you think your API design is good (also define what your measure of good is)?
    * It is simple and readable and easy to understand.


###Comments About Partner's API: Aaric Han ###

**How does your API encapsulate your implementation decisions?**

getPlayerColors() - gets combo boxes and returns them to the user as a list of nodes


**What abstractions is your API built on?**

Returns a list of nodes to be read by another class and get player's colors.

**What about your API's design is intended to be flexible?**

Returns a node, so there are many different possible methods of getting a color value.

**What exceptions (error cases) might occur in your API and how are they addressed?**

Nope.

##Part 2 ##

**How do you justify that your API is easy to learn?**

It doesn't take any parameters- none required to get the player's colors.

**How do you justify that API leads to readable code?**

Name is very clear as to what it is trying to do.

**How do you justify that API is hard to misuse?**

Every dependency that it is uses is already created at the start of the program and cannot be modified.

**Why do you think your API design is good (also define what your measure of good is)?**

It's simple and does one thing, and it does it well.