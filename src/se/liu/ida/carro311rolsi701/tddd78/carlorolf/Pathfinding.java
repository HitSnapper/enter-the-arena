package se.liu.ida.carro311rolsi701.tddd78.carlorolf;

/**
 * Created by HitSnapper on 2016-07-18.
 */
final class Pathfinding {
    public static void main(){

    }

}
/*/
Main() {
    g(sstart) := 0;
    parent(sstart) := sstart;
    open := ∅;
    open.Insert(sstart 5 , g(sstart) + h(sstart));
    closed := ∅;
    while (open = ∅) {
        s := open.Pop();
        if (s = sgoal) {
            return “path found”;
        }
        closed := closed ∪ {s};
        foreach (s′ ∈ nghbrsvis(s)) {
            if (s′ ∈ closed) {
                if (s′ ∈ open){
                    g(s′) := ∞;
                    parent(s') := NULL;
                }
                UpdateVertex(s, s′);
            }
        }
    }
    return “no path found”;
}


UpdateVertex(s,s’) {
    if LineOfSight(parent(s), s′ 42 ) {
        if (g(parent(s)) + c(parent(s), s′) < g(s′))) {
            g(s′) := g(parent(s)) + c(parent(s), s′);
            parent(s′) := parent(s);
            if (s′ ∈ open) {
                open.Remove(s′);
            }
            open.Insert(s′, g(s′) + h(s′));
        }
    }
    else {
        if g(s) + c(s, s′) < g(s′) {
            g(s′) := g(s) + c(s, s′ 53 );
            parent(s′ 54 ) := s;
            if (s′ ∈ open) {
                open.Remove(s′ 56 );
            }
            open.Insert(s′, g(s′) + h(s′));
        }
    }
}

*/


/*
LINE OF SIGHT ALGORITHM

LineOfSight(s, s’){
    x0 := s.x;
    y0 := s.y;
    x1 := s′.x;
    y1 := s′.y;
    dy := y1 − y0;
    dx := x1 − x0;
    f := 0;
    if (dy < 0) {
        dy := −dy;
        sy := −1;
    }
    else {
        sy := 1;
    }
    if (dx < 0) {
        dx := −dx;
        sx := −1;
    }
    else {
        sx := 1;
    }
    if (dx ≥ dy) {
        while (x0 6= x1) {
            f := f + dy;
            if (f ≥ dx) {
                if (grid(x0 + ((sx − 1)/2), y0 + ((sy − 1)/2))) {
                    return false;
                }
                y0 := y0 + sy;
                f := f − dx;
            }
            if (f != 0 AND grid(x0 + ((sx − 1)/2), y0 + ((sy − 1)/2))) {
                return false;
            }
            if (dy = 0 AND grid(x0 + ((sx − 1)/2), y0) AND grid(x0 + ((sx − 1)/2), y0 − 1)) {
                return false;
            }
            x0 := x0 + sx;
        }
    }
    else {
        while (y0 6= y1) {
            f := f + dx;
            if (f ≥ dy) {
                if (grid(x0 + ((sx − 1)/2), y0 + ((sy − 1)/2))) {
                    return false;
                }
                x0 := x0 + sx;
                f := f − dy;
            }
            if (f != 0 && grid(x0 + ((sx − 1)/2), y0 + ((sy − 1)/2))) {
                return false;
            }
            if (dx = 0 AND grid(x0, y0 + ((sy − 1)/2)) AND grid(x0 − 1, y0 + ((sy − 1)/2))) {
                return false;
            }
            y0 := y0 + sy;
        }
    }
    return true;
}

*/