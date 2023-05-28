package barbershop.helper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.OfficerDataAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import java.util.*;

public class BarbershopUtil {

    public static List<PersonAPI> getOfficers() {
        List<PersonAPI> officers = new ArrayList();
        if (Global.getSector().getPlayerFleet() != null) {

            Iterator iterator = Global.getSector().getPlayerFleet().getFleetData().getOfficersCopy().iterator();

            PersonAPI captain;
            while (iterator.hasNext()) {
                OfficerDataAPI officer = (OfficerDataAPI) iterator.next();
                captain = officer.getPerson();
                officers.add(captain);
            }

            iterator = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy().iterator();

            while (iterator.hasNext()) {
                FleetMemberAPI member = (FleetMemberAPI) iterator.next();
                captain = member.getCaptain();
                if (captain != null && !captain.isDefault() && !officers.contains(captain)) {
                    officers.add(captain);
                }
            }

        }
        return officers;
    }

    public static List<String> getPortraits() {
        Set<String> allPortraits = new HashSet<>();

        List<String> anyPortraits = Global.getSector().getPlayerFaction().getPortraits(FullName.Gender.ANY).getItems();
        List<String> malePortraits = Global.getSector().getPlayerFaction().getPortraits(FullName.Gender.MALE).getItems();
        List<String> femalePortraits = Global.getSector().getPlayerFaction().getPortraits(FullName.Gender.FEMALE).getItems();

        allPortraits.addAll(anyPortraits);
        allPortraits.addAll(malePortraits);
        allPortraits.addAll(femalePortraits);

        return new ArrayList<>(allPortraits);
    }
}
