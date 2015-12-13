var express = require('express');
var request = require('request');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
	res.render('index');
});

router.get('/getResults', function(req, res, next){
	req.cache.hgetall("words", function(err,result){
		if(err == null && result!=null){
			var hashmap = result;
			var list = [];

			for (var m in hashmap){
				list.push(m+"_"+hashmap[m]);
			}

			res.send(list);
		}
	});

});

module.exports = router;
