Array.prototype.contains = function(item) {
	for (i = 0; i < this.length; i++) {
		if (this[i] == item) {
			return true;
		}
	}
	return false;
};

Array.prototype.remove=function(element){
    for(i=0;i<this.length;i++){//遍历数组对象的元素
       if(this[i]==element){  //当相等时
          this.splice(i,1);   //调用javascript的内置方法splice(规定要删除的项目的位置,要删除的项目数量)，返回删除后的this。
       }
    }
    return this;   //返回调用remove方法的对象
}


String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}