// Copyright 2020 Sebastian Kuerten
//
// This file is part of gradle-cache-busting-plugin.
//
// gradle-cache-busting-plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// gradle-cache-busting-plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with gradle-cache-busting-plugin. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.gradle;

import java.nio.file.Path;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;

public class CacheBustingPlugin implements Plugin<Project>
{

	@Override
	public void apply(final Project project)
	{
		Logger logger = project.getLogger();
		logger.info("applying cache busting plugin");

		if (!project.getPlugins().hasPlugin(WarPlugin.class)) {
			logger.error("Please enable the war plugin.");
			throw new IllegalStateException("No war plugin detected.");
		}

		CacheBustingPluginExtension extension = project.getExtensions().create(
				"cacheBusting",
				CacheBustingPluginExtension.class);

		GenerateStaticFilesTask task = project.getTasks().create(
				"generateStaticFiles",
				GenerateStaticFilesTask.class);
		task.setConfiguration(extension);

		project.getTasks().findByName("compileJava").dependsOn(task);

		if (project.getPlugins().hasPlugin(EclipsePlugin.class)) {
			project.getTasks().findByName("eclipse").dependsOn(task);
			project.getTasks().findByName("eclipseClasspath").dependsOn(task);
			project.getTasks().findByName("eclipseProject").dependsOn(task);
		}
	}

}
