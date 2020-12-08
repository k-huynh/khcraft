# KHCraft
KHCraft is a papermc plugin used by JJJ to change the progression system of vanilla Minecraft. Don't judge the name plz I couldn't come up with a better one 🙂

# Status
### Broken 💔 maybe 71% done? 
<br>

## Still to-do (in no particular order)
- change loot tables
- custom items (resource pack)
- custom achievements
- cap repair costs for elytra in anvil
- update 'help' info with skills (i.e. replace the 'todo' and stuff)
- make EnchantmentListener less computationally intensive -- i.e. dont check every item every time
- test everything properly lol
- rename enchantments nicely for upgrade/select without breaking everything
- add more meaningful console logs

## Doing
- commands for economy
    - /buy itemName (for custom items)

## Tested as working
- allow creative flight when holding item in off-hand
- adding 'unsafe' enchantments to items
- tested general exp (lvl 29 --> 30 --> 0) = +1 available skill point
- tested mining exp (lvl 0 --> 1 --> 2 --> 3) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested chopping exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested digging exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested farming exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested combat exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested fishing exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested archery exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested trident exp (lvl 0 --> 1) recognised level up, (lvl 4 --> 5) = +1 available skill point
- tested upgrading enchantments for mining, archery, and chopping
- tested selecting enchantments for mining, archery, and chopping
- tested automatic enchanting of tools based on selecting enchantments
- tested disabling of enchantments for tools based on skill/tool
- right clicking villagers (with professions) do not bring up the trading gui
- tested enchanting flint and steel, and warped fungus on a stick
- /skills display correctly displays xp and current level
- skill exp no longer double counting level ups (and hence skill point awarding) (i think)
- test that skill point requirements and level requirements for enchants are working correctly (ACCORDING TO CHEAH)
- /trade @to {amount} {message (optional)} works
- /skills output is now aligned better (although it still kinda sucks sorry)
- /balance {optional: playername} works
- can only use the hover item with elytra equipped
- can only hover if the specified hover item is in offhand
- /shop lists custom items specified in config

## Fully Completed
- getting exp from mining
- getting exp from chopping
- getting exp from digging
- getting exp from farming
- getting exp from fishing
- getting exp from combat
- getting exp from trident usage
- getting exp from archery
- custom skillExpEvent calling and listening
- give general skill points on (vanilla) level up
- implement exp curves for skills to determine levels
- award skill points every 5 skill levels
- storing enchantment information in config/database
- implement method to select enchantments (owned)
- implement method to upgrade enchantments (from available enchantments)
- Should notify on skill level up
- /skills should show all your xp
- implement auto-enchanting tools in inventory based on userenchantments
- add a way to remove all selected enchantments for a skill/tool
- disable villager trading
- make sure flint and steel, carrot and warped fungus on stick enchantments are handled properly 
- test that you dont get doubly awarded skill points
- trading KB and logging the transaction
- make /skills output look nicer (a little lol)
- /balance (gives you your KB balance) (should also be able to show other players balances)
- /shop (lists server store items?)

## Future tasks if I can be bothered
- GUI for 'server store'
- GUI for player stores
- GUI for selecting skills
- GUI for choosing new enchantments
- add a way to 'unspec' skill points/reset skill trees
- additional commands for skills i.e.
    - /givesp @to skillName n (server operator only) (award skill points)
- additional commands for economy, i.e.
    - /givekb @to amount 'msg' (server operator only)

<br>

# Notes
- did not do any further testing for exp level up events
- POTENTIAL ISSUE: due to floating point operations, there may be inaccuracies with calculating level up bounds. Most of these should be within +/- 1 exp event but i haven't tested nor verified the logic to be 100% correct. Worst case scenarios: not recognising level up resulting in missed skill point awards, or doubly recognising a level up resulting in double skill point awards
- note: will not count if you skip multiples of 5 levels towards giving skill points :(
- removed level requirements for general enchantments (since there isnt really a notion of 'levels' (yet) for general skills)
- NOTE: partial issue regarding resultset queries when checking for incompatible enchantments; not sure if it poses any functional risks though
- realistically i should be creating wrapper classes for stuff like enchantments and custom items but im lazy :) maybe one day i'll do it