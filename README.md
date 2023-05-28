# BARBERSHOP

Change your officers' portraits.

## Requirements

[Console Commands](https://fractalsoftworks.com/forum/index.php?topic=4106.0)

## Usage

Open the console (by default bound to **control+backspace**)

Use the command `barbershop <officer name> <portrait id>`

`<officer name>` Can be either the full name, first name, or last name of the officer.

`<portrait id>` is the filename of the portrait you want to change to.

ex. `barbershop Ultra Deimos portrait_diktat07`
`barbershop Deneb portrait29`

Look for the filenames in mod folders as well as in `starsector-core/graphics/portraits`.

This will only work for portraits that are marked as available for use by the player faction.

### SUPER REDACTED TECH

To force change to a reserved portrait (such as a named character), pass the relative path of the portrait file from the mod or starsector-core directory and the `--force` flag. 

ex. `barbershop Minerva Europa graphics/portraits/characters/andrada1.png --force`

Note: Not all files work. If you get a blank/black portrait, use the regular command to reassign the portrait back.
