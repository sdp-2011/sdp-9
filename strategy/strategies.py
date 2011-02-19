import main
import null
import kicktest

try:
    import main2
    from mlbridge import MLBridge
except ImportError:
    MLBridge = None

"A list of strategies that can be used"

strategies = { 'main' : main.Main,
               'main2' : main2.Main,
               'kicktest'   : kicktest.KickTest,
               'null' : null.Null,
               'ML'   : MLBridge,
             }

def list_strategies():
    for strat in strategies:
        print strat

