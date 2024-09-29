import os
import xml.etree.ElementTree as ET
from git import Repo


def clone_repo(repo_url, clone_dir):
    try:
        Repo.clone_from(repo_url, clone_dir)
        print(f'Repository cloned to {clone_dir}')
    except Exception as e:
        print(f'Error cloning repository: {e}')


def run_git_diff(file_path):
    try:
        repo = Repo(os.getcwd()+"/cloned-repo")
        diff = repo.git.diff(file_path)
        print(diff)
    except Exception as e:
        print(f'Error running git diff: {e}')


def get_version_from_pom(xmlfile):
    # Parse the XML file
    tree = ET.parse(xmlfile)
    root = tree.getroot()
    # Define the namespace
    namespace = {'mvn': 'http://maven.apache.org/POM/4.0.0'}
    # Find the version element
    version_element = root.find('mvn:version', namespace)

    # Extract and return the text value of the version element
    if version_element is not None:
        return version_element.text
    else:
        return None


def detect_changes():
    #clone_repo(os.getenv('REPO_URL'), "cloned-repo")
    is_auth_svc_changed = False
    is_user_svc_changed = False
    is_product_svc_changed = False
    run_git_diff("auth-svc/pom.xml")
detect_changes()

