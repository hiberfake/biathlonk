#!/bin/bash
# Script to generate module graphs for each of the modules.
# The '--exclude-module' parameter can be used to exclude modules which are not part of the root graph.
#
# Usage:
# ```bash
# ./generateModuleGraphs.sh --exclude-module :benchmark
# ```

# Check if Graphviz is installed.
if ! command -v dot &>/dev/null; then
  echo "Graphviz is not installed. This is required to generate SVGs from the Graphviz files."
  echo "Installation instructions:"
  echo "  - On macOS: You can install Graphviz using Homebrew with the command: 'brew install graphviz'"
  echo "  - On Ubuntu: You can install Graphviz using APT with the command: 'sudo apt-get install graphviz'"
  echo "  - Others: Visit https://graphviz.org/download/."
  exit 1
fi

# Check if the 'svgo' command is available.
if ! command -v svgo &>/dev/null; then
  echo "The 'svgo' command is not found. This is required to cleanup and compress SVGs."
  echo "Installation instructions available at https://github.com/svg/svgo."
  exit 1
fi

# Check for a version of grep which supports Perl regex.
# On MacOS the OS installed grep doesn't support Perl regex so check for the existence of the
# GNU version instead which is prefixed with 'g' to distinguish it from the OS installed version.
if command -v ggrep >/dev/null 2>&1; then
  GREP_COMMAND=ggrep
else
  GREP_COMMAND="grep"
fi

# Initialize an array to store the excluded modules.
excluded_modules=()

# Parse command-line arguments for the excluded modules.
while [[ $# -gt 0 ]]; do
  case "$1" in
  --exclude-module)
    excluded_modules+=("$2")
    shift # Past argument
    shift # Past value
    ;;
  *)
    echo "Unknown parameter passed: $1"
    exit 1
    ;;
  esac
done

# Get the module paths.
module_paths=$(${GREP_COMMAND} -oP 'include\("\K[^"]+' settings.gradle.kts)

# Ensure the output directory exists.
mkdir -p docs/images/graphs/

# Function to check and create a README.md for modules which don't have one.
check_and_create_readme() {
  local module_path="$1"
  local filename="$2"

  local readme_path="${module_path:1}" # Remove leading colon
  readme_path=${readme_path//:/\/}     # Replace colons with slashes
  readme_path="$readme_path/README.md" #Append the filename

  # Check if README.md exists and create it if not.
  if [[ ! -f "$readme_path" ]]; then
    echo "Creating README.md for $module_path ..."

    # Determine the depth of the module based on the number of colons.
    local depth
    depth=$(awk -F: '{print NF-1}' <<<"$module_path")

    # Construct the relative image path with the correct number of "../".
    local relative_image_path="../"
    for ((i = 1; i < depth; i++)); do
      relative_image_path+="../"
    done
    relative_image_path+="docs/images/graphs/$filename.svg"

    {
      echo "## Module Graph"
      echo ""
      echo "![Module graph]($relative_image_path)"
    } >>"$readme_path"
  fi
}

# Loop through each module path.
echo "$module_paths" | while read -r module_path; do
  # Check if the module is in the excluded list.
  if [[ ! " ${excluded_modules[*]} " =~ ${module_path} ]]; then
    # Derive the filename from the module path.
    filename="module-graph${module_path//:/-}" # Replace colons with dashes

    check_and_create_readme "$module_path" "$filename"

    # Generate the .gv file in a temporary location.
    # '</dev/null' is used to stop './gradlew' from consuming input which prematurely ends the while loop.
    ./gradlew generateModulesGraphvizText \
      -Pmodules.graph.output.gv="/tmp/$filename.gv" \
      -Pmodules.graph.of.module="$module_path" </dev/null

    # Convert to SVG using 'dot'.
    dot -Tsvg "/tmp/$filename.gv" >"docs/images/graphs/$filename.svg"
    # Remove the temporary .gv file.
    rm "/tmp/$filename.gv"
  fi
done

# Compress SVGs using 'svgo'.
svgo -rf docs/images/graphs --multipass --pretty
