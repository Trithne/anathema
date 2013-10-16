package net.sf.anathema.character.main.magic.charm.prerequisite;

import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.character.main.magic.charm.Charm;
import net.sf.anathema.character.main.magic.charm.prerequisite.impl.AttributeKnownCharmLearnPrerequisite;
import net.sf.anathema.character.main.magic.charm.prerequisite.impl.DirectGroupCharmLearnPrerequisite;
import net.sf.anathema.character.main.magic.charm.prerequisite.impl.IndirectGroupCharmLearnPrerequisite;
import net.sf.anathema.character.main.magic.charm.prerequisite.impl.SimpleCharmLearnPrerequisite;
import net.sf.anathema.character.main.magic.charm.requirements.CharmAttributeRequirement;
import net.sf.anathema.character.main.magic.charm.requirements.IndirectCharmRequirement;
import net.sf.anathema.character.main.magic.charm.requirements.SelectiveCharmGroup;
import net.sf.anathema.character.main.magic.parser.charms.CharmPrerequisiteList;

public class CharmLearnPrerequisiteBuilder {
	private final CharmPrerequisiteList prerequisiteList;
	private final Charm[] parents;
	private final SelectiveCharmGroup[] openGroups;
	private final SelectiveCharmGroup[] combinedGroups;
	
	public CharmLearnPrerequisiteBuilder(CharmPrerequisiteList list, Charm[] parents,
			SelectiveCharmGroup[] openGroups, SelectiveCharmGroup[] combinedGroups) {
		this.prerequisiteList = list;
		this.parents = parents;
		this.openGroups = openGroups;
		this.combinedGroups = combinedGroups;
	}
	
	public CharmLearnPrerequisite[] getPrerequisites() {
		// TODO: Most of this code is temporary, translating the various already
		// existing forms of prerequisites in to a new paradigm. Next step is
		// to substitute the places the originals are currently built with the new classes.
		List<CharmLearnPrerequisite> prerequisites = new ArrayList<>();
		
		for (Charm charm : parents) {
			prerequisites.add(new SimpleCharmLearnPrerequisite(charm));
		}
		for (SelectiveCharmGroup group : openGroups) {
			prerequisites.add(new DirectGroupCharmLearnPrerequisite(group.getAllGroupCharms(), group.getThreshold()));
		}
		for (SelectiveCharmGroup group : combinedGroups) {
			prerequisites.add(new IndirectGroupCharmLearnPrerequisite(group.getLabel(), group.getAllGroupCharms(), group.getThreshold()));
		}
		for (IndirectCharmRequirement requirement : prerequisiteList.getAttributeRequirements()) {
			if (requirement instanceof CharmAttributeRequirement) {
				CharmAttributeRequirement attribute = (CharmAttributeRequirement)requirement;
				prerequisites.add(new AttributeKnownCharmLearnPrerequisite(attribute.getAttribute(), attribute.getThreshold()));
			}
		}
		
		return prerequisites.toArray(new CharmLearnPrerequisite[0]);
	}
}
