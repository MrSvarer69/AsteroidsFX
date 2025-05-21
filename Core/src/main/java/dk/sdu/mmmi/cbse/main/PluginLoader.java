package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.lang.module.ModuleFinder;
import java.nio.file.Paths;
import java.util.ServiceLoader;
import java.util.Set;

public class PluginLoader {
    public static ModuleLayer loadPluginLayer(String pluginsDir, String moduleName) {
        var finder = ModuleFinder.of(Paths.get(pluginsDir));
        var parent = ModuleLayer.boot();
        var cf = parent.configuration().resolve(finder, ModuleFinder.of(), Set.of(moduleName));
        return parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
    }

    public static void main(String[] args) {
        // Example usage: load Enemy module from plugins
        var layer = loadPluginLayer("plugins", "Enemy");
        var plugins = ServiceLoader.load(layer, IGamePluginService.class);
        plugins.forEach(plugin -> System.out.println("Loaded plugin: " + plugin.getClass()));
    }
}