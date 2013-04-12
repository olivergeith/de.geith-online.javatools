# ins repository Verzeichnis wechseln
cd /var/lib/jenkins/.m2/repository/
# alle Dateien und Verzeichnisse ins Subversion Repository kopieren
cp -r * /var/lib/jenkins/m2repos/
# ins Subversion Repository wechseln
cd /var/lib/jenkins/m2repos/

# alle Dateien zum hinzufügen markieren
# Hinweis: mit --force werden auch Verzeichnisse rekursiv nach Dateien durchsucht, die bereits unter Versionskontrolle stehen
svn add * --force
# Dateien nach Subversion übertragen 
svn commit --username build --password build --no-auth-cache --message "Aenderungen durch Maven-Build"
