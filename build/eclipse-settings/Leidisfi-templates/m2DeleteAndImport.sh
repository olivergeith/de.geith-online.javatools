# ins repository Verzeichnis wechseln
cd /var/lib/jenkins/.m2/repository
# Verzeichnis leeren
rm -r -f *
# zum Subversion m2repos Workspace wechseln
cd /var/lib/jenkins/m2repos
# svn update
svn update --username build --password build --no-auth-cache
cp -r * ../.m2/repository/




