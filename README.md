# KHCraft
KHCraft is a papermc plugin used by JJJ to change the progression system of vanilla Minecraft. Don't judge the name plz I couldn't come up with a better one 🙂

# Status
### Broken 💔 maybe 65% done? 
<br>

## Still to-do (in no particular order)
- change loot tables
- custom items (resource pack)
- commands for economy
    - /trade @to amount 'msg'
    - /shop (lists server store items?)
    - /buy itemName
    - /givekb @to amount 'msg' (server operator only)
- custom achievements
- cap repair costs for elytra in anvil
- update 'help' info with skills (i.e. replace the 'todo' and stuff)
- make EnchantmentListener less computationally intensive -- i.e. dont check every item every time
- rename enchantments nicely for upgrade/select without breaking everything
- test everything properly lol
- make /skills output look nicer

## Doing
- test that you dont get doubly awarded skill points
- test that skill point requirements and level requirements for enchants are working correctly
- make sure flint and steel enchantments are handled properly (the way im handling the strings probably wont work 😪)


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

## Future tasks if I can be bothered
- GUI for 'server store'
- GUI for player stores
- GUI for selecting skills
- GUI for choosing new enchantments
- add a way to 'unspec' skill points/reset skill trees
- additional commands for skills i.e.
    - /givesp @to skillName n (server operator only) (award skill points)

<br>

# Notes
- did not do any further testing for exp level up events
- POTENTIAL ISSUE: due to floating point operations, there may be inaccuracies with calculating level up bounds. Most of these should be within +/- 1 exp event but i haven't tested nor verified the logic to be 100% correct. Worst case scenarios: not recognising level up resulting in missed skill point awards, or doubly recognising a level up resulting in double skill point awards