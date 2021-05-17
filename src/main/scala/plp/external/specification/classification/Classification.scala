package plp.external.specification.classification

import plp.external.specification.identity.Identity

import plp.external.specification.composition.Composition

trait Classification[>-->[- _, + _]]
  extends Identity[>-->]
  , Composition[>-->]