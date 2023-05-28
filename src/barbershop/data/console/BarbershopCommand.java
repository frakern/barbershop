package barbershop.data.console;

import barbershop.helper.BarbershopUtil;
import org.jetbrains.annotations.NotNull;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;

import java.util.*;

import org.lazywizard.console.BaseCommand;
import org.lazywizard.console.CommandUtils;
import org.lazywizard.console.CommonStrings;
import org.lazywizard.console.Console;

public class BarbershopCommand implements BaseCommand {

    @Override
    public CommandResult runCommand(@NotNull String args, @NotNull CommandContext context) {
        if (context == CommandContext.COMBAT_MISSION) {
            Console.showMessage(CommonStrings.ERROR_CAMPAIGN_ONLY);
            return CommandResult.WRONG_CONTEXT;
        }

        if (args.isEmpty()) {
            return CommandResult.BAD_SYNTAX;
        }

        String[] tmp = args.split(" ");

        if (tmp.length < 2) {
            return CommandResult.BAD_SYNTAX;
        }

        boolean force = false;
        String spriteID;
        StringBuilder builder = new StringBuilder();

        if (Objects.equals(tmp[tmp.length - 1], "--force")) {
            force = true;
            spriteID = tmp[tmp.length - 2];
            for (int i = 0; i < tmp.length - 2; i++) {
                builder.append(tmp[i]);
            }
        }
        else {
            spriteID = tmp[tmp.length - 1];
            for (int i = 0; i < tmp.length - 1; i++) {
                builder.append(tmp[i]);
            }
        }

        // Match sprite filename.
        if (!force) {
            List<String> allPortraits = BarbershopUtil.getPortraits();
            if (!allPortraits.contains(spriteID)) {
                List<String> spriteNames = new ArrayList<>();

                for (String spritePath : allPortraits) {
                    String[] path = spritePath.split("/");
                    spriteNames.add(path[path.length -1]);
                }

                String bestStringMatch = CommandUtils.findBestStringMatch(spriteID, spriteNames);
                if (bestStringMatch != null) {
                    spriteID = allPortraits.get(getStringIndexInList(bestStringMatch, spriteNames));
                }
                else {
                    Console.showMessage("Sprite filename not found matching ID: " + spriteID);
                    return CommandResult.ERROR;
                }
            }
        }

        // Match officer name.
        String nameSearch = builder.toString();
        PersonAPI selectedOfficer = null;

        List<String> officerFullNames = new ArrayList<>();
        List<String> officerLastNames = new ArrayList<>();
        List<String> officerFirstNames = new ArrayList<>();
        List<PersonAPI> officersList = BarbershopUtil.getOfficers();

        for (PersonAPI person : officersList) {
            FullName name = person.getName();
            officerFullNames.add(name.getFullName());
            officerLastNames.add(name.getLast());
            officerFirstNames.add(name.getFirst());
        }

        String bestFullName = CommandUtils.findBestStringMatch(nameSearch, officerFullNames);
        if (bestFullName != null) {
            selectedOfficer = officersList.get(getStringIndexInList(bestFullName, officerFullNames));
        }
        else {
            String bestLastName = CommandUtils.findBestStringMatch(nameSearch, officerLastNames);
            if (bestLastName != null) {
                selectedOfficer = officersList.get(getStringIndexInList(bestLastName, officerLastNames));
            }
            else {
                String bestFirstName = CommandUtils.findBestStringMatch(nameSearch, officerFirstNames);
                if (bestFirstName != null) {
                    selectedOfficer = officersList.get(getStringIndexInList(bestFirstName, officerFirstNames));
                }
            }
        }
        if (selectedOfficer == null) {
            Console.showMessage("Unable to find officer with name " + nameSearch);
            return CommandResult.ERROR;
        }

        selectedOfficer.setPortraitSprite(spriteID);

        Console.showMessage("Changed portrait sprite for " + selectedOfficer.getName().getFullName());

        return CommandResult.SUCCESS;
    }

    protected int getStringIndexInList(String str, List<String> list) {
        for (int i=0; i < list.size(); i++) {
            Global.getLogger(this.getClass()).info(str + ", " + list.get(i));
            String entry = list.get(i);
            if (entry.equals(str)) return i;
        }
        return -1;
    }

}