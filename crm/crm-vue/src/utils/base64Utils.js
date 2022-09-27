/*转换base64格式*/
let base={
    getBase64Str(file) {
        return new Promise(resolve => {
            //获取信息
            let fileReader = new FileReader();
            /*转换为base64*/
            fileReader.readAsDataURL(file)
            //发送
            fileReader.onload = function (e) {
                resolve(e.target.result);
            }
        })
    }
}
export default  base;