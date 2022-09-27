<template>


    <div class="login-wrapper">

        <div class="login-left">
            <img src="https://juheimgs.oss-cn-beijing.aliyuncs.com/banner/201901/f7d057623e6ac570.jpeg" alt="">
        </div>


        <div class="login-right">
            <div class="login-form-wrapper">
                <!--        logo -->
                <div class="form-logo">
                </div>

                <!--   标题-->
                <div class="form-title">
                    <h3>账号登录</h3>
                </div>

                <div class="login-form">
                    <div class="form-item">
                        <i class="el-icon-s-custom"></i>
                        <input type="text" placeholder="请输入用户名" v-model="loginForm.username">
                    </div>
                    <div class="form-item">
                        <i class="el-icon-house"></i>
                        <input type="text" placeholder="请输入密码" v-model="loginForm.password">
                    </div>
                    <div class="form-item-code">
                        <div class="form-item code-item">
                            <i class="el-icon-house"></i>
                            <input type="text" placeholder="请输入验证码" v-model="loginForm.captcha">
                        </div>

                        <div class="img-code">
                            <img :src="base64Str" @click="getCaptcha" alt="">
                        </div>
                    </div>

                    <div class="form-item">
                        <button @click="doLogin">登录</button>
                    </div>
                </div>

            </div>


        </div>
    </div>
</template>

<script>
    import  login from "./index.js"
    export default login
</script>

<style scoped lang="scss">
    .login-wrapper {
        width: 100%;
        height: 100%;
        position: relative;

        .login-left {
            height: 100%;
            width: 60%;
            position: absolute;
            left: 0;
            top: 0;
            background-color: #0f0f0f;

            img {
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
                vertical-align: middle;
            }

        }

        .login-right {
            height: 100%;
            width: 40%;
            position: absolute;
            top: 0;
            right: 0;

        }

        .login-form-wrapper {
            width: 65%;
            height: 400px;

            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);

            .form-logo {
                width: 195px;
                height: 50px;
                /*background-image: url('../../assets/huige.png');*/
            }

            .form-title {
                margin-top: 20px;
                height: 45px;

                h3 {
                    color: #00BDFF;
                    float: left;
                    font-weight: 400;
                    line-height: 40px;
                    border-bottom: 3px solid #00BDFF;
                }
            }

            .login-form {
                margin-top: 10px;

                .form-item {
                    border-bottom: 1px solid #e7e7e7;
                    line-height: 60px;

                    i {
                        color: #00BDFF;
                        font-size: 20px;
                    }

                    input {
                        border: none;
                        outline: none;
                        text-indent: 10px;
                        color: #606060;
                        font-size: 14px;
                        height: 45px;
                    }

                    button {
                        width: 100%;
                        height: 50px;
                        background-color: rgb(3, 197, 255);
                        border: none;
                        margin-top: 30px;
                        border-radius: 3px;
                        color: white;
                        outline: none;

                    }
                }

                .form-item:nth-child(4) {
                    border-bottom: none;
                }

            }
        }

        .code-item {
            float: left;
            width: 60%;
        }

        .form-item-code .img-code {
            float: left;
            padding-left: 10px;
            box-sizing: border-box;
            width: 40%;
            height: 100%;
            line-height: 60px;

            img {
                width: 130px;
                height: 48px;
            }
        }
        /**
        修改 placeHolder的颜色
        */
        input::-webkit-input-placeholder {
            /* WebKit browsers */
            color: #ccc;
        }

        input:-moz-placeholder {
            /* Mozilla Firefox 4 to 18 */
            color: #ccc;
        }

        input::-moz-placeholder {
            /* Mozilla Firefox 19+ */
            color: #ccc;
        }

        input:-ms-input-placeholder {
            /* Internet Explorer 10+ */
            color: #ccc;
        }

    }
</style>