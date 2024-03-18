package com.priximmo.geojson.feuille;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.priximmo.abstractcomponent.AbstractFeatures;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureFeuille extends AbstractFeatures<FeuilleProperties> {
}
