var express = require('express');
var request = require('request');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {

	req.cache.hgetall("words", function(err,result){
		if(err == null && result!=null){
			var hashmap = result;
			var list = [];

			console.log(hashmap);
			for (var m in hashmap){
				console.log(m);
				list.push(m+"_"+hashmap.m);
			}

			res.render('index', { title: 'Express', result: list});

		}
	});
});
function printe(result){
	for (var i = 0; i < result.length; i +1) {
     console.log(result[i])
    }
    return true;
}
module.exports = router;
