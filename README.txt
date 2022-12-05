Groupe :
	21801009 Alexander Camatchy
	21914304 Alix Danvy
	22005759 Mathis Roptin
	22003660 Félix Sénéchal 
	
Mode d'emploi:
	Note: pour compiler correctement, il est nécessaire d'avoir
		un répertoire lib contenant la librairie de tests fourni 
		(à côté du répertoire src)
		
	script ant:
		$ ant : pour compiler le projet.
		
        $ ant compile : pour compiler le projet.
		
        $ ant doc : pour générer la documentation du projet.
		
        $ ant test : pour lancer les tests sur tous 
				les éléments du projet.
		
    fr -> $ ant testBooleanWorld : pour lancer les tests sur WorldWithBooleanVariable

        $ ant testQueue : pour lancer les tests sur PriorityQueueHeap

    fr ->$ ant filRouge1 : pour lancer la première démonstration/tests du blockworld pour des mondes régulier.

    fr ->$ ant plannerBW : Pour lancer la démo/comparaison des Planner sur le blockworld. Voir Doc & commentaire dans le code pour plus d'informations (ainsi que plus bas pour une conclusion sur les résultats).

    fr ->$ ant dataminingBW : Pour lancer la démo du datamining sur le blockworld en analysant le fonction de la librairie bwgenerator fourni.

    fr ->$ ant cspBW : Pour lancer la démo de la recherche d'état satisfaisant les contraintes régulières d'un blockworld avec les méthodes vues.

        $ant benchminer :  pour lancer les benchmark de comparaison
				entre Apriori et FPGrowth.
				notez que: il est nécessaire d'avoir créer le répertoire
				'benchmark_results'. (résultats sous forme de csv)
        


		$python3 scripts/plotmaker.py : pour transormer les résultats du
				benchmark réalisé précedement en graphiques.

Note:
	On trouvera les conclusions/observations quand aux résultats des démo du CSP et du datamining 
    directement dans les fichier sources concernés ou dans le terminal lors de leur execution.

    PS: normalement on a choisi des réglages raisonnable pour la démo des planner mais il se peut
    que le Planner DFS fasse exploser la limite de récursion maximum de la JVM en fonction de votre configuration et des réglages.

Conclusion/observations sur les tests et démonstration des planner:
    Pour commencer, en ce qui concerne les résultats observé, on voit très clairement une 
    performance bien plus grande des algorithmes utilisant des heuristiques, autant en temps de calcul
    qu'en nombre de noeud exploré pour finir par obtenir un plan qui est toujours optimal (sauf peut-être parfois pour BeamSearch en fonction de sa vision).

    De plus, on a pu constater que les mauvaises performance de DFS vont parfois au delà d'une recherche sous-optimal et pouvant
    mené à faire l'expérience d'erreurs de Stack Overflow lorsque le problème étudié est trop grand pour lui.