(function (e, t, n, r, i, s, o) {
    function d(r) {
        if (Object.prototype.toString.apply(r) === "[object Array]") {
            if (typeof r[0] == "string" && typeof d[r[0]] == "function")return new d[r[0]](r.slice(1, r.length));
            if (r.length === 4)return new d.RGB(r[0] / 255, r[1] / 255, r[2] / 255, r[3] / 255)
        } else if (typeof r == "string") {
            var i = r.toLowerCase();
            a[i] && (r = "#" + a[i]), i === "transparent" && (r = "rgba(0,0,0,0)");
            var s = r.match(p);
            if (s) {
                var o = s[1].toUpperCase(), u = f(s[8]) ? s[8] : t(s[8]), l = o[0] === "H", h = s[3] ? 100 : l ? 360 : 255, v = s[5] || l ? 100 : 255, m = s[7] || l ? 100 : 255;
                if (f(d[o]))throw new Error("one.color." + o + " is not installed.");
                return new d[o](t(s[2]) / h, t(s[4]) / v, t(s[6]) / m, u)
            }
            r.length < 6 && (r = r.replace(/^#?([0-9a-f])([0-9a-f])([0-9a-f])$/i, "$1$1$2$2$3$3"));
            var g = r.match(/^#?([0-9a-f][0-9a-f])([0-9a-f][0-9a-f])([0-9a-f][0-9a-f])$/i);
            if (g)return new d.RGB(n(g[1], 16) / 255, n(g[2], 16) / 255, n(g[3], 16) / 255);
            if (d.CMYK) {
                var y = r.match(new e("^cmyk\\(" + c.source + "," + c.source + "," + c.source + "," + c.source + "\\)$", "i"));
                if (y)return new d.CMYK(t(y[1]) / 100, t(y[2]) / 100, t(y[3]) / 100, t(y[4]) / 100)
            }
        } else if (typeof r == "object" && r.isColor)return r;
        return !1
    }

    function v(e, t, n) {
        function l(e, t) {
            var n = {};
            n[t.toLowerCase()] = new r("return this.rgb()." + t.toLowerCase() + "();"), d[t].propertyNames.forEach(function (e, i) {
                n[e] = n[e === "black" ? "k" : e[0]] = new r("value", "isDelta", "return this." + t.toLowerCase() + "()." + e + "(value, isDelta);")
            });
            for (var i in n)n.hasOwnProperty(i) && d[e].prototype[i] === undefined && (d[e].prototype[i] = n[i])
        }

        d[e] = new r(t.join(","), "if (Object.prototype.toString.apply(" + t[0] + ") === '[object Array]') {" + t.map(function (e, n) {
                return e + "=" + t[0] + "[" + n + "];"
            }).reverse().join("") + "}" + "if (" + t.filter(function (e) {
                return e !== "alpha"
            }).map(function (e) {
                return "isNaN(" + e + ")"
            }).join("||") + "){" + 'throw new Error("[' + e + ']: Invalid color: ("+' + t.join('+","+') + '+")");}' + t.map(function (e) {
                return e === "hue" ? "this._hue=hue<0?hue-Math.floor(hue):hue%1" : e === "alpha" ? "this._alpha=(isNaN(alpha)||alpha>1)?1:(alpha<0?0:alpha);" : "this._" + e + "=" + e + "<0?0:(" + e + ">1?1:" + e + ")"
            }).join(";") + ";"), d[e].propertyNames = t;
        var s = d[e].prototype;
        ["valueOf", "hex", "hexa", "css", "cssa"].forEach(function (t) {
            s[t] = s[t] || (e === "RGB" ? s.hex : new r("return this.rgb()." + t + "();"))
        }), s.isColor = !0, s.equals = function (n, r) {
            f(r) && (r = 1e-10), n = n[e.toLowerCase()]();
            for (var s = 0; s < t.length; s += 1)if (i.abs(this["_" + t[s]] - n["_" + t[s]]) > r)return !1;
            return !0
        }, s.toJSON = new r("return ['" + e + "', " + t.map(function (e) {
                return "this._" + e
            }, this).join(", ") + "];");
        for (var o in n)if (n.hasOwnProperty(o)) {
            var a = o.match(/^from(.*)$/);
            a ? d[a[1].toUpperCase()].prototype[e.toLowerCase()] = n[o] : s[o] = n[o]
        }
        s[e.toLowerCase()] = function () {
            return this
        }, s.toString = new r('return "[one.color.' + e + ':"+' + t.map(function (e, n) {
                return '" ' + t[n] + '="+this._' + e
            }).join("+") + '+"]";'), t.forEach(function (e, n) {
            s[e] = s[e === "black" ? "k" : e[0]] = new r("value", "isDelta", "if (typeof value === 'undefined') {return this._" + e + ";" + "}" + "if (isDelta) {" + "return new this.constructor(" + t.map(function (t, n) {
                    return "this._" + t + (e === t ? "+value" : "")
                }).join(", ") + ");" + "}" + "return new this.constructor(" + t.map(function (t, n) {
                    return e === t ? "value" : "this._" + t
                }).join(", ") + ");")
        }), u.forEach(function (t) {
            l(e, t), l(t, e)
        }), u.push(e)
    }

    var u = [], a = {}, f = function (e) {
        return typeof e == "undefined"
    }, l = /\s*(\.\d+|\d+(?:\.\d+)?)(%)?\s*/, c = /\s*(\.\d+|100|\d?\d(?:\.\d+)?)%\s*/, h = /\s*(\.\d+|\d+(?:\.\d+)?)\s*/, p = new e("^(rgb|hsl|hsv)a?\\(" + l.source + "," + l.source + "," + l.source + "(?:," + h.source + ")?" + "\\)$", "i");
    d.installMethod = function (e, t) {
        u.forEach(function (n) {
            d[n].prototype[e] = t
        })
    }, v("RGB", ["red", "green", "blue", "alpha"], {
        hex: function () {
            var e = (s(255 * this._red) * 65536 + s(255 * this._green) * 256 + s(255 * this._blue)).toString(16);
            return "#" + "00000".substr(0, 6 - e.length) + e
        }, hexa: function () {
            var e = s(this._alpha * 255).toString(16);
            return "#" + "00".substr(0, 2 - e.length) + e + this.hex().substr(1, 6)
        }, css: function () {
            return "rgb(" + s(255 * this._red) + "," + s(255 * this._green) + "," + s(255 * this._blue) + ")"
        }, cssa: function () {
            return "rgba(" + s(255 * this._red) + "," + s(255 * this._green) + "," + s(255 * this._blue) + "," + this._alpha + ")"
        }
    }), typeof define == "function" && !f(define.amd) ? define(function () {
        return d
    }) : typeof exports == "object" ? module.exports = d : (one = window.one || {}, one.color = d), typeof jQuery != "undefined" && f(jQuery.color) && (jQuery.color = d), v("HSV", ["hue", "saturation", "value", "alpha"], {
        rgb: function () {
            var e = this._hue, t = this._saturation, n = this._value, r = o(5, i.floor(e * 6)), s = e * 6 - r, u = n * (1 - t), a = n * (1 - s * t), f = n * (1 - (1 - s) * t), l, c, h;
            switch (r) {
                case 0:
                    l = n, c = f, h = u;
                    break;
                case 1:
                    l = a, c = n, h = u;
                    break;
                case 2:
                    l = u, c = n, h = f;
                    break;
                case 3:
                    l = u, c = a, h = n;
                    break;
                case 4:
                    l = f, c = u, h = n;
                    break;
                case 5:
                    l = n, c = u, h = a
            }
            return new d.RGB(l, c, h, this._alpha)
        }, hsl: function () {
            var e = (2 - this._saturation) * this._value, t = this._saturation * this._value, n = e <= 1 ? e : 2 - e, r;
            return n < 1e-9 ? r = 0 : r = t / n, new d.HSL(this._hue, r, e / 2, this._alpha)
        }, fromRgb: function () {
            var e = this._red, t = this._green, n = this._blue, r = i.max(e, t, n), s = o(e, t, n), u = r - s, a, f = r === 0 ? 0 : u / r, l = r;
            if (u === 0)a = 0; else switch (r) {
                case e:
                    a = (t - n) / u / 6 + (t < n ? 1 : 0);
                    break;
                case t:
                    a = (n - e) / u / 6 + 1 / 3;
                    break;
                case n:
                    a = (e - t) / u / 6 + 2 / 3
            }
            return new d.HSV(a, f, l, this._alpha)
        }
    }), v("HSL", ["hue", "saturation", "lightness", "alpha"], {
        hsv: function () {
            var e = this._lightness * 2, t = this._saturation * (e <= 1 ? e : 2 - e), n;
            return e + t < 1e-9 ? n = 0 : n = 2 * t / (e + t), new d.HSV(this._hue, n, (e + t) / 2, this._alpha)
        }, rgb: function () {
            return this.hsv().rgb()
        }, fromRgb: function () {
            return this.hsv().hsl()
        }
    })
})(RegExp, parseFloat, parseInt, Function, Math, Math.round, Math.min)
