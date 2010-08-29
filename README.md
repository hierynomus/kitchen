# Spring Integration Kitchen 
This project is an example application for the Xebia NLJUG University Session on Spring Integration.

# Setting up the project
The project is built with Gradle, and doesn't contain any IDE files. In order to create them, you can do the following:

* For IntelliJ IDEA
		gradle idea
* For Eclipse
		gradle eclipse

Gradle 0.9 or higher is required. You can download the latest version from http://www.gradle.org/.

# The Dinner example
Let’s say you’re going to have guests for dinner. Maybe you’re not the cooking kind, but you are most likely familiar with the concept of home cooking and that’s more than enough. We shall look at the whole process of preparing and serving the meal and see what would be involved in terms of messaging to automate this. Just for the sake of the argument we will ignore the possibility of ordering food - which would surely decrease the complexity of the setup enormously.

Involved in the dinner will be the host, the kitchen, the guests and the shops. The host will orchestrate the whole thing, the guests will consume the end product and turn it into entertaining small talks and other things irrelevant to our story. The kitchen is the framework that the host uses to transform the ingredients he will get from the shop into the actual dinner.

The sequence of events after a date has been set is the part we’re interested in. It starts with the host selecting a menu and gathering the relevant recipes from his cook books or the internet. The ingredients needed for the recipes are then to be bought at the shops, but to buy them one at a time, with a roundtrip to the shop for each product is unreasonably inefficient, so the host needs to process them in a smarter way. The trick is then for the host to create a shopping list for each shop in advance. He does this by picking the ingredients from each recipe one by one and putting them on the appropriate shopping list. He then does a trip to each shop and delivers the ingredients back to his kitchen.

With the ingredients in the house each shopping bag needs to be unpacked and ingredients for the right dish put together. This is what professional cooks call the mis en place. Our host will do the same to impress his guests of course. Then each dish can be put together, this usually involves putting the ingredients together in some way in a large container. When the dish is done it is divided over plates and it can be served.

But what does this have to do with messaging? Well, it might be more than you think. Let’s say the recipe is a message. Then it would be fair to say this message is split into ingredients, which can be sent as messages by themselves. These ingredients (messages) then reach another endpoint that aggregates them onto the appropriate shopping list. The shopping lists (messages) then travel to the shops where the shopper turns them into bags filled with groceries (messages) which travel back to the kitchen (endpoint). 

The shopping bags are unpacked (split) into products that are put together in different configurations on the counter during the mis an place. These groups of ingredients are then transformed into a course in a pan. The dish in the pan is split onto different plates (messages) which then travel to the endpoints that consume them at the table.

What we’ve seen here is a lot of breaking apart and putting together of payloads. The recipes are split and the ingredients aggregated into grocery lists. The bags are unpacked and the products regrouped for the different courses. The dishes are split and assembled on plates. Some observations can be made from this analogy that are helpful to think back on later in the chapter.

Splitting is a relatively easy job. It is however important to keep track of which ingredient belongs to which recipe. This is called the correlation id. We’re aggregating in a different configuration than we were originally splitting for. Most easy examples of splitting and aggregating will use a splitter that takes a thing apart and an aggregator that turns all the split parts into a thing again. In fact this is a simplistic example, so it’s good to keep a simple analogy in mind that does things differently. In a real enterprise an aggregator will very often not have a symmetric splitter. 

It now becomes important to think on how we will know when the aggregate is done. Or to stay with the example, when is the shopping list done? The answer here is that it is only done when all the recipes have been split and all ingredients are on a list. This is still relatively simple, but far more interesting that to say that a list is done when all ingredients of one particular recipe are on it.

When aggregating is done without a previous symmetric splitting, it also becomes harder to figure out which messages belong together. Or, in this example, which ingredients go on which list. Usually it is not some key on the message itself natively, but a key that needs to be generated by a business rule. For example it could be that all ingredients that are a vegetable will go on the greenery list.

# The Labs
Each lab can be found in a branch or the repository:

* lab-1: Deal with converting a recipe into its separate ingredients.
* lab-2: Deal with converting the ingredients of a recipe into their physical counterparts by shopping for them.
* lab-3: Deal with getting all the products back together to create a meal
* lab-4: Reading the recipes from the recipebook/filesystem
* lab-5: Try to minimize the time spent shopping by making shoppinglists and grocerybags
