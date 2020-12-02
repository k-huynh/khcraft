# KHCraft
KHCraft is a papermc plugin used by JJJ to change the progression system of vanilla Minecraft. Don't judge the name plz I couldn't come up with a better one 🙂

# Status
### Broken 💔 maybe 40-45% done? 
<br>

## Still to-do (in no particular order)
- implement auto-enchanting tools in inventory based on userenchantments
- implement method to select enchantments (owned)
- implement method to upgrade enchantments on level up (if available)
- change loot tables
- disable villager trading
- custom items (resource pack)
- commands for economy
    - /trade @to amount 'msg'
    - /shop (lists server store items?)
    - /buy itemName
    - /givekb @to amount 'msg' (server operator only)
- commands for skills
    - /skills (lists all skills, exp, levels)
    - /mining upgrade (?)
    - /mining select (?)
- custom achievements
- cap repair costs for elytra/tridents in anvil

## Doing
- database/config schema for enchantments (link to skill, level requirements)
- populate enchantments table
- give general skill points on (vanilla) level up
- implement exp curves for skills to determine levels

## Tested as working
- allow creative flight when holding item in off-hand
- adding 'unsafe' enchantments to items

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

## Future tasks if I can be bothered
- GUI for 'server store'
- GUI for player stores
- GUI for selecting skills
- GUI for choosing new enchantments
