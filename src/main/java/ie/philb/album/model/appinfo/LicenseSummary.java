/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model.appinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class LicenseSummary {
    
    private final List<Dependency> dependencies = new ArrayList<>();
    
    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies.clear();
        this.dependencies.addAll(dependencies);
    }
    
    public List<Dependency> getDependencies() {
        return Collections.unmodifiableList(dependencies);
    }

    @Override
    public String toString() {
        return "LicenseSummary{" + "dependencies=" + dependencies + '}';
    }
    
    
}
