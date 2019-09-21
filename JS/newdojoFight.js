function getDirection(current,target) {
    var dx = target.getX()-current.getX();
    var dy = current.getY()-target.getY();
    if (dx!==0) return dx>0? 'RIGHT' : 'LEFT';
    else return dy>0? 'DOWN' : 'UP';
  }
  function getMostClosestPointForTarget(points, target){
    var result = points[0];
    [...points].forEach((point) => { result = range(point,target) < range(result,target) ? point : result;  });
    return result;
  }
  function getAllGoldTargets(scanner) {
      var gold = scanner.findAll("GOLD");
      if (gold.length>0) return gold.filter((e) => { 
                     return {x:e.getX(),y:e.getY()}
      });
      else 
      return [];
  }
  function range (p1,p2) {
    return Math.sqrt(Math.pow((p1.getX()-p2.getX()),2) + Math.pow((p1.getY()-p2.getY()),2));
  }
  
  
  function program(robot) {
    // напиши тут свой код. 
    //todo  Научить перепрыгивать дыры. 
    //todo  Искать альтернативные пути
    while(true) 
    try   {
      var scanner = robot.getScanner();
      
      var me = scanner.getMe();
      var goldTargets = getAllGoldTargets(scanner);
      var target;
      if (goldTargets.length>0) {
          target = getMostClosestPointForTarget(goldTargets,me);
      } else target = scanner.findAll("EXIT")[0];
      
      var wayToTarget = scanner.getShortestWay(target);
      var firstMove = wayToTarget[1];
      //   robot.log('me:' + me);  robot.log('exit:' + exitTarget);  robot.log('move:' + firstMove);
    
      var direction = getDirection(me,firstMove);
  
      robot.go(direction);
      break;
    } catch (err) {
          robot.log('ошибка' + err);  
          break;
        
    }
    
  }
  
  
  
  